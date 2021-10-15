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
public class SearchController extends HttpServlet {

    private final String MAIN_PAGE = "home.jsp";
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
        String sessionTxtSearch = (String) session.getAttribute("SEARCH");
        String sessionIndexPage = (String) session.getAttribute("INDEXPAGE");
        String sessionSearchOption = (String) session.getAttribute("OPTSEARCH");
        String sessionMinPrice = String.valueOf(session.getAttribute("MINPRICE"));
        String sessionMaxPrice = String.valueOf(session.getAttribute("MAXPRICE"));
        String sessionSelectedCate = String.valueOf(session.getAttribute("SELECTEDCATE"));
        //New search option
        String opt = request.getParameter("SearchOpt");
        if (opt != null) {
            sessionSearchOption = opt;
        }
        //Init search by name load
        if (sessionTxtSearch == null) {
            sessionTxtSearch = "";
        }
        //Init min price
        if (sessionMinPrice.equals("null")) {
            sessionMinPrice = "0";
        }
        //Init max price
        if (sessionMaxPrice.equals("null")) {
            sessionMaxPrice = "0";
        }
        //Init category selected
        if (sessionSelectedCate.equals("null")) {
            sessionSelectedCate = "0";
        }
        //Init page load
        if (sessionIndexPage == null) {
            sessionIndexPage = "1";
        }
        //Init option load
        if (sessionSearchOption == null) {
            sessionSearchOption = "SearchByName";
        }
        //Get request parameter
        String txtSearch = request.getParameter("txtSearch");
        String txtSelectCate =  request.getParameter("category");
        String txtMinPrice = request.getParameter("minPrice");
        String txtMaxPrice = request.getParameter("maxPrice");
        //Reset index page
        if (!sessionTxtSearch.equals(txtSearch) || !sessionSelectedCate.equals(txtSelectCate) || !sessionMinPrice.equals(txtMinPrice) || !sessionMaxPrice.equals(txtMaxPrice)) {
            sessionIndexPage = "1";
        }
        //New name search
        if (txtSearch != null) {
            sessionTxtSearch = txtSearch;
        }
        //New category search
        if (txtSelectCate != null && FormatSupport.isNumeric(txtSelectCate)) {
            sessionSelectedCate = txtSelectCate;
        }
        //New min price
        if (txtMinPrice != null && FormatSupport.isNumeric(txtMinPrice)) {
            sessionMinPrice = txtMinPrice;
        }
        //New max price
        if (txtMaxPrice != null && FormatSupport.isNumeric(txtMaxPrice)) {
            sessionMaxPrice = txtMaxPrice;
        }
        //New index page
        String index = request.getParameter("indexPage");
        if (index != null) {
            sessionIndexPage = index;
        }
        ProductDAO productDAO = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        try {
            //Convert to numeric
            int categoryId = Integer.parseInt(sessionSelectedCate);
            BigDecimal minPriceDecimal = new BigDecimal(sessionMinPrice);
            BigDecimal maxPriceDecimal = new BigDecimal(sessionMaxPrice);
            //Calculate total pages
            int total = 0;
            if (sessionSearchOption.equals("SearchByName")) {
                total = productDAO.getTotalProductPageSearchName(sessionTxtSearch, NUM_PER_PAGE);
            } else if (sessionSearchOption.equals("SearchByPrice")) {
                total = productDAO.getTotalProductPageSearchPrice(minPriceDecimal, maxPriceDecimal, NUM_PER_PAGE);
            } else if (sessionSearchOption.equals("SearchByCategory")) {
                total = productDAO.getTotalProductPageSearchCategory(categoryId, NUM_PER_PAGE);
            }
            //Convert and check index page
            int indexPage = Integer.parseInt(sessionIndexPage);
            if (indexPage > total) {
                indexPage = 1;
                sessionIndexPage = "" + indexPage;
            }
            //OFFSET_START
            int offset = (NUM_PER_PAGE * (indexPage - 1));
            if (offset < 0) {
                offset = 0;
            }
            //Get products
            //Get product by name
            if (sessionSearchOption.equals("SearchByName")) {
                productDAO.getProductsOnName(offset, NUM_PER_PAGE, sessionTxtSearch);
                System.out.println(sessionTxtSearch);
            } //Get product by price
            else if (sessionSearchOption.equals("SearchByPrice")) {
                productDAO.getProductsOnPrice(minPriceDecimal, maxPriceDecimal, offset, NUM_PER_PAGE);
            } //Get product by category
            else if (sessionSearchOption.equals("SearchByCategory")) {
                productDAO.getProductsOnCategory(categoryId, offset, NUM_PER_PAGE);
            }
            List<ProductDTO> result = productDAO.getList();
            //Get Categories
            categoryDAO.getCategories();
            List<CategoryDTO> cateList = categoryDAO.getList();
            //Set and clear attributes
            request.setAttribute("LIST", result);
            request.setAttribute("CATE", cateList);
            request.setAttribute("PAGES", total);
            session.setAttribute("INDEXPAGE", sessionIndexPage);
            session.setAttribute("OPTSEARCH", sessionSearchOption);
            if (sessionSearchOption.equals("SearchByName")) {
                session.setAttribute("SEARCH", sessionTxtSearch);
                session.removeAttribute("MINPRICE");
                session.removeAttribute("MAXPRICE");
                session.removeAttribute("SELECTEDCATE");
            } else if (sessionSearchOption.equals("SearchByPrice")) {
                session.setAttribute("MINPRICE", sessionMinPrice);
                session.setAttribute("MAXPRICE", sessionMaxPrice);
                session.removeAttribute("SEARCH");
                session.removeAttribute("SELECTEDCATE");
            } else if (sessionSearchOption.equals("SearchByCategory")) {
                session.setAttribute("SELECTEDCATE", sessionSelectedCate);
                session.removeAttribute("MINPRICE");
                session.removeAttribute("MAXPRICE");
                session.removeAttribute("SEARCH");
            }

        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass().getName() + ": " + e.getMessage());
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
