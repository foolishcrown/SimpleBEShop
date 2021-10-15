/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import sangnv.account.AccountDTO;
import sangnv.utils.MultipartFileProcess;
import sangnv.utils.MyConstants;

/**
 *
 * @author Shang
 */
public class MainController extends HttpServlet {

    private final String SEARCH_CONTROLLER = "SearchController";
    private final String LOGIN_CONTROLLER = "LoginController";
    private final String LOGIN_GOOGLE_CONTROLLER = "LoginGoogleController";
    private final String LOGOUT_CONTROLLER = "LogoutController";
    private final String ADD_CART_CONTROLLER = "AddCartController";
    private final String UPDATE_CART_CONTROLLER = "UpdateCartController";
    private final String REMOVE_CART_CONTROLLER = "RemoveCartController";
    private final String CONFIRM_CART_CONTROLLER = "ConfirmCartController";
    private final String ADMIN_SEARCH_CONTROLLER = "AdminSearchController";
    private final String GET_CREATE_FORM_CONTROLLER = "GetCreateFormController";
    private final String CREATE_PRODUCT_CONTROLLER = "CreateProductController";
    private final String DISABLE_PRODUCT_CONTROLLER = "DisableProductController";
    private final String EDIT_PRODUCT_CONTROLLER = "EditProductController";
    private final String UPDATE_PRODUCT_CONTROLLER = "UpdateProductController";
    private final String TRACKING_ORDER_CONTROLLER = "TrackingOrderController";
    private final String TRACKING_ORDER_PAGE = "trackingorder.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String url = SEARCH_CONTROLLER;
        String action;
        AccountDTO account = (AccountDTO) (request.getSession()).getAttribute("ACCOUNT");
        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                HashMap<String, String> params = MultipartFileProcess.getParams(getServletContext(), request);
                action = params.get("action");
                if (action.equals("CreateProduct") || action.equals("UpdateProduct")) {
                    if (account != null && account.getRole() == MyConstants.ROLE_ADMIN) {
                        HttpSession session = request.getSession();
                        session.setAttribute("productId", params.get("productId"));
                        session.setAttribute("productImage", params.get("productImage"));
                        session.setAttribute("productName", params.get("productName"));
                        session.setAttribute("productDescription", params.get("productDescription"));
                        session.setAttribute("productPrice", params.get("productPrice"));
                        session.setAttribute("createdDate", params.get("createdDate"));
                        session.setAttribute("expirationDate", params.get("expirationDate"));
                        session.setAttribute("category", params.get("category"));
                        session.setAttribute("productQuantity", params.get("productQuantity"));
                        session.setAttribute("fullPathURL", params.get("fullPathURL"));
                    }
                }
            } else {
                action = request.getParameter("action");
            }

            if (account != null && account.getRole() == MyConstants.ROLE_ADMIN) {
                url = ADMIN_SEARCH_CONTROLLER;
            }

            if (action == null) {

            } else if (action.equals("Login")) {
                url = LOGIN_CONTROLLER;
            } else if (action.equals("Logout")) {
                url = LOGOUT_CONTROLLER;
            } else if (action.equals("LoginGoogle")) {
                url = LOGIN_GOOGLE_CONTROLLER;
            } else if (action.equals("Search")) {
                url = SEARCH_CONTROLLER;
            } else if (action.equals("AddCart")) {
                if (account != null && account.getRole() == MyConstants.ROLE_ADMIN) {
                    url = ADMIN_SEARCH_CONTROLLER;
                } else {
                    url = ADD_CART_CONTROLLER;
                }
            } else if (action.equals("UpdateCart")) {
                if (account != null && account.getRole() == MyConstants.ROLE_ADMIN) {
                    url = ADMIN_SEARCH_CONTROLLER;
                } else {
                    url = UPDATE_CART_CONTROLLER;
                }
            } else if (action.equals("RemoveCart")) {
                if (account != null && account.getRole() == MyConstants.ROLE_ADMIN) {
                    url = ADMIN_SEARCH_CONTROLLER;
                } else {
                    url = REMOVE_CART_CONTROLLER;
                }
            } else if (action.equals("ConfirmCart")) {
                if (account != null && account.getRole() == MyConstants.ROLE_ADMIN) {
                    url = ADMIN_SEARCH_CONTROLLER;
                } else {
                    url = CONFIRM_CART_CONTROLLER;
                }
            } else if (action.equals("ViewTrackPage")) {
                if (account != null && account.getRole() == MyConstants.ROLE_ADMIN) {
                    url = ADMIN_SEARCH_CONTROLLER;
                } else if (account != null && account.getRole() == MyConstants.ROLE_CUSTOMER) {
                    url = TRACKING_ORDER_PAGE;
                } else {
                    url = SEARCH_CONTROLLER;
                }
            } else if (action.equals("TrackingOrder")) {
                if (account != null && account.getRole() == MyConstants.ROLE_ADMIN) {
                    url = ADMIN_SEARCH_CONTROLLER;
                } else if (account != null && account.getRole() == MyConstants.ROLE_CUSTOMER) {
                    url = TRACKING_ORDER_CONTROLLER;
                } else {
                    url = SEARCH_CONTROLLER;
                }
            } else if (action.equals("AdminSearch")) {
                if (account != null && account.getRole() == MyConstants.ROLE_ADMIN) {
                    url = ADMIN_SEARCH_CONTROLLER;
                }
            } else if (action.equals("GetCreateForm")) {
                if (account != null && account.getRole() == MyConstants.ROLE_ADMIN) {
                    url = GET_CREATE_FORM_CONTROLLER;
                }
            } else if (action.equals("CreateProduct")) {
                if (account != null && account.getRole() == MyConstants.ROLE_ADMIN) {
                    url = CREATE_PRODUCT_CONTROLLER;
                }
            } else if (action.equals("DisableProduct")) {
                if (account != null && account.getRole() == MyConstants.ROLE_ADMIN) {
                    url = DISABLE_PRODUCT_CONTROLLER;
                }
            } else if (action.equals("EditProduct")) {
                if (account != null && account.getRole() == MyConstants.ROLE_ADMIN) {
                    url = EDIT_PRODUCT_CONTROLLER;
                }
            } else if (action.equals("UpdateProduct")) {
                if (account != null && account.getRole() == MyConstants.ROLE_ADMIN) {
                    url = UPDATE_PRODUCT_CONTROLLER;
                }
            }
        } catch (FileUploadException ex) {
            log("File Upload Exception at " + this.getClass().getName() + ": " + ex.getMessage());
        } catch (Exception ex) {
            log("Error Exception at " + this.getClass().getName() + ": " + ex.getMessage());
        } finally {
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
