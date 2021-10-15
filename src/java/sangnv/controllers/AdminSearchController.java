/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sangnv.category.CategoryDAO;
import sangnv.category.CategoryDTO;
import sangnv.product.ProductDAO;
import sangnv.product.ProductDTO;
import sangnv.utils.FormatSupport;
import sangnv.utils.MyConstants;

/**
 *
 * @author Shang
 */
public class AdminSearchController extends HttpServlet {

    private final String MAIN_PAGE = "admin.jsp";
    private final int NUM_PER_PAGE = MyConstants.TOTAL_ITEM_IN_PAGE;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = MAIN_PAGE;
        //Get session
        HttpSession session = request.getSession();
        //Get request
        String savedSearch = (String) session.getAttribute("SEARCH");
        String indexPage = (String) session.getAttribute("INDEXPAGE");
        String searchOpt = (String) session.getAttribute("OPTSEARCH");
        String savedMin = String.valueOf(session.getAttribute("MINPRICE"));
        String savedMax = String.valueOf(session.getAttribute("MAXPRICE"));
        String selectedCate = String.valueOf(session.getAttribute("SELECTEDCATE"));
        String selectedStatus = String.valueOf(session.getAttribute("SELECTEDSTATUS"));
        //New search option
        String opt = request.getParameter("SearchOpt");
        if (opt != null) {
            searchOpt = opt;
        }
        //Init search by name load
        if (savedSearch == null) {
            savedSearch = "";
        }
        //Init min price
        if (savedMin.equals("null")) {
            savedMin = "0";
        }
        //Init max price
        if (savedMax.equals("null")) {
            savedMax = "0";
        }
        //Init category selected
        if (selectedCate.equals("null")) {
            selectedCate = "0";
        }
        //Init status selected
        if (selectedStatus.equals("null")) {
            selectedStatus = "1";
        }
        //Init page load
        if (indexPage == null) {
            indexPage = "1";
        }
        //Init option load
        if (searchOpt == null) {
            searchOpt = "SearchByName";
        }
        //Get request parameter
        String search = request.getParameter("txtSearch");
        String newCate = request.getParameter("category");
        String newStatus = request.getParameter("status");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        //Reset index page
        if (!savedSearch.equals(search) || !selectedCate.equals(newCate) || !selectedStatus.equals(newStatus) || !savedMin.equals(minPrice) || !savedMax.equals(maxPrice)) {
            indexPage = "1";
        }
        //New name search
        if (search != null) {
            savedSearch = search;
        }
        //New category search
        if (newCate != null && FormatSupport.isNumeric(newCate)) {
            selectedCate = newCate;
        }
        //New status search
        if (newStatus != null && FormatSupport.isNumeric(newStatus)) {
            selectedStatus = newStatus;
        }
        //New min price
        if (minPrice != null && FormatSupport.isNumeric(minPrice)) {
            savedMin = minPrice;
        }
        //New max price
        if (maxPrice != null && FormatSupport.isNumeric(maxPrice)) {
            savedMax = maxPrice;
        }
        //New index page
        String index = request.getParameter("indexPage");
        if (index != null) {
            indexPage = index;
        }
        ProductDAO dao = new ProductDAO();
        CategoryDAO cateDao = new CategoryDAO();
        try {
            //Convert to numeric
            int cateId = Integer.parseInt(selectedCate);
            int statusId = Integer.parseInt(selectedStatus);
            BigDecimal minPriceDecimal = new BigDecimal(savedMin);
            BigDecimal maxPriceDecimal = new BigDecimal(savedMax);
            //Calculate total pages
            int total = 0;
            if (searchOpt.equals("SearchByName")) {
                total = dao.getTotalProductPageSearchNameByAdmin(savedSearch, statusId, NUM_PER_PAGE);
            } else if (searchOpt.equals("SearchByPrice")) {
                total = dao.getTotalProductPageSearchPriceByAdmin(minPriceDecimal, maxPriceDecimal, statusId, NUM_PER_PAGE);
            } else if (searchOpt.equals("SearchByCategory")) {
                total = dao.getTotalProductPageSearchCategoryByAdmin(cateId, statusId, NUM_PER_PAGE);
            }
            //Convert and check index page
            int indexPageNum = Integer.parseInt(indexPage);
            if (indexPageNum > total) {
                indexPageNum = 1;
                indexPage = "" + indexPageNum;
            }
            //OFFSET_START
            int offset = (NUM_PER_PAGE * (indexPageNum - 1));
            if (offset < 0) {
                offset = 0;
            }
            //Get products
            //Get product by name
            if (searchOpt.equals("SearchByName")) {
                dao.getProductsOnNameByAdmin(offset, NUM_PER_PAGE, statusId, savedSearch);
                System.out.println(savedSearch);
            } //Get product by price
            else if (searchOpt.equals("SearchByPrice")) {
                dao.getProductsOnPriceByAdmin(minPriceDecimal, maxPriceDecimal, statusId, offset, NUM_PER_PAGE);
            } //Get product by category
            else if (searchOpt.equals("SearchByCategory")) {
                dao.getProductsOnCategoryByAdmin(cateId, statusId, offset, NUM_PER_PAGE);
            }
            List<ProductDTO> result = dao.getList();
            //Get Categories
            cateDao.getCategories();
            List<CategoryDTO> cateList = cateDao.getList();
            //Set and clear attributes
            request.setAttribute("LIST", result);
            request.setAttribute("CATE", cateList);
            request.setAttribute("PAGES", total);
            session.setAttribute("INDEXPAGE", indexPage);
            session.setAttribute("OPTSEARCH", searchOpt);
            session.setAttribute("SELECTEDSTATUS", selectedStatus);
            if (searchOpt.equals("SearchByName")) {
                session.setAttribute("SEARCH", savedSearch);
                session.removeAttribute("MINPRICE");
                session.removeAttribute("MAXPRICE");
                session.removeAttribute("SELECTEDCATE");
            } else if (searchOpt.equals("SearchByPrice")) {
                session.setAttribute("MINPRICE", savedMin);
                session.setAttribute("MAXPRICE", savedMax);
                session.removeAttribute("SEARCH");
                session.removeAttribute("SELECTEDCATE");
            } else if (searchOpt.equals("SearchByCategory")) {
                session.setAttribute("SELECTEDCATE", selectedCate);
                session.removeAttribute("MINPRICE");
                session.removeAttribute("MAXPRICE");
                session.removeAttribute("SEARCH");
            }
            
        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (Exception e) {
            log("Error Exception at " + this.getClass().getName() + ": " + e.getMessage());
        } finally {
            //Forward
            request.getRequestDispatcher(url).forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
