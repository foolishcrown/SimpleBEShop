/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import sangnv.account.AccountDTO;
import sangnv.product.ProductDAO;
import sangnv.product.ProductDTO;
import sangnv.utils.FormatSupport;

/**
 *
 * @author Shang
 */
public class UpdateProductController extends HttpServlet {

    private final String ADMIN_PAGE = "AdminSearchController";
    private final String EDIT_PAGE = "EditProductController";

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
        //Get request parameters
        String productId,
                productImage,
                productName,
                productDescription,
                productPrice,
                createdDate,
                expirationDate,
                categoryId,
                productQuantity;

        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                //Get all string params
                HttpSession session = request.getSession();
                AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");

                productId = (String) session.getAttribute("productId");
                productImage = (String) session.getAttribute("productImage");
                productName = (String) session.getAttribute("productName");
                productDescription = (String) session.getAttribute("productDescription");
                productPrice = (String) session.getAttribute("productPrice");
                createdDate = (String) session.getAttribute("createdDate");
                expirationDate = (String) session.getAttribute("expirationDate");
                categoryId = (String) session.getAttribute("category");
                productQuantity = (String) session.getAttribute("productQuantity");

                //Preapare number params
                BigDecimal price = null;
                int productIdNum = 0;
                int categoryIdNum = 0;
                int quantity = 0;
                Date createdDateSql = null;
                Date expirationDateSql = null;

                boolean checkValid = true;

                if (productName == null) {
                    checkValid = false;
                } else {
                    productName = new String(productName.getBytes("iso-8859-1"), "utf-8");
                }
                if (productDescription == null) {
                    checkValid = false;
                } else {
                    productDescription = new String(productDescription.getBytes("iso-8859-1"), "utf-8");
                }
                if (productPrice == null || !FormatSupport.isNumeric(productPrice)) {
                    checkValid = false;
                } else {
                    price = new BigDecimal(productPrice);
                }
                if (productId == null || !FormatSupport.isNumeric(productId)) {
                    checkValid = false;
                } else {
                    productIdNum = Integer.parseInt(productId);
                }
                if (categoryId == null || !FormatSupport.isNumeric(categoryId)) {
                    checkValid = false;
                } else {
                    categoryIdNum = Integer.parseInt(categoryId);
                }
                if (productQuantity == null || !FormatSupport.isNumeric(productQuantity)) {
                    checkValid = false;
                } else {
                    quantity = Integer.parseInt(productQuantity);
                }
                if (createdDate == null || !FormatSupport.isSQLDate(createdDate)) {
                    checkValid = false;
                } else {
                    createdDateSql = Date.valueOf(createdDate);
                }
                if (expirationDate == null || !FormatSupport.isSQLDate(expirationDate)) {
                    checkValid = false;
                } else {
                    expirationDateSql = Date.valueOf(expirationDate);
                }

                if (account == null) {
                    checkValid = false;
                }

                if (checkValid) {
                    ProductDTO dto = new ProductDTO();
                    dto.setId(productIdNum);
                    dto.setImageURL(productImage);
                    dto.setName(productName);
                    dto.setDescription(productDescription);
                    dto.setPrice(price);
                    dto.setCreatedDate(createdDateSql);
                    dto.setExpirationDate(expirationDateSql);
                    dto.setCateId(categoryIdNum);
                    dto.setQuantity(quantity);
                    dto.setLastUpdateUser(account.getId());
                    ProductDAO dao = new ProductDAO();
                    boolean isCreated = dao.updateProduct(dto);
                    if (!isCreated) {
                        if (session.getAttribute("fullPathURL") != null) {
                            File tmpImg = new File((String) session.getAttribute("fullPathURL"));
                            tmpImg.delete();
                        }
                        request.setAttribute("ErrorMSG", "Updated Failed.");
                        url = EDIT_PAGE;
                    } else {
                        session.removeAttribute("productId");
                        session.removeAttribute("productImage");
                        session.removeAttribute("productName");
                        session.removeAttribute("productDescription");
                        session.removeAttribute("productPrice");
                        session.removeAttribute("createdDate");
                        session.removeAttribute("expirationDate");
                        session.removeAttribute("category");
                        session.removeAttribute("productQuantity");
                    }
                } else {
                    if (session.getAttribute("fullPathURL") != null) {
                        File tmpImg = new File((String) session.getAttribute("fullPathURL"));
                        tmpImg.delete();
                    }
                    request.setAttribute("ErrorMSG", "Please check your data again and re-create it.");
                    url = EDIT_PAGE;
                }
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
