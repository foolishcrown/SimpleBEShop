/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sangnv.account.AccountDAO;
import sangnv.category.CategoryDAO;
import sangnv.category.CategoryDTO;
import sangnv.product.ProductDAO;
import sangnv.product.ProductDTO;
import sangnv.utils.FormatSupport;

/**
 *
 * @author Shang
 */
public class EditProductController extends HttpServlet {

    private final String ADMIN_PAGE = "AdminSearchController";
    private final String EDIT_FORM = "editproduct.jsp";

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
        String url = ADMIN_PAGE;
        String itemId = request.getParameter("itemId");
        ProductDTO dto = null;
        ProductDAO dao = new ProductDAO();
        try {
            if (itemId != null && FormatSupport.isNumeric(itemId)) {
                int itemIdNum = Integer.parseInt(itemId);
                dto = dao.findEditProductById(itemIdNum);
                if (dto != null) {
                    AccountDAO daoAccount = new AccountDAO();
                    String lastUpdateUserName = daoAccount.getStringUserById(dto.getLastUpdateUser());
                    
                    request.setAttribute("LastUpdateUser", lastUpdateUserName);
                    request.setAttribute("DTO", dto);
                    url = EDIT_FORM;
                } else {
                    request.setAttribute("MsgError", "You can not update this product, it's already out of stock or expired!");
                    url = ADMIN_PAGE;
                }
            }
            CategoryDAO cateDao = new CategoryDAO();
            //Get Categories
            cateDao.getCategories();
            List<CategoryDTO> cateList = cateDao.getList();
            request.setAttribute("CATE", cateList);
        }  catch (NamingException e) {
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
