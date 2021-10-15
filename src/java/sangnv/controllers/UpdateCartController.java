/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sangnv.utils.FormatSupport;
import sangnv.utils.ShoppingCart;

/**
 *
 * @author Shang
 */
public class UpdateCartController extends HttpServlet {

    private final String CART_PAGE = "viewcart.jsp";

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
        String url = CART_PAGE;
        //Get Session
        HttpSession session = request.getSession();
        //Get session request
        ShoppingCart cart = (ShoppingCart) session.getAttribute("CART");
        //New cart
        if (cart == null) {
            cart = new ShoppingCart();
        }
        //Get update type
        String changeButton = request.getParameter("ChangeButton");
        String itemId = request.getParameter("itemId");
        try {
            if (itemId != null && FormatSupport.isNumeric(itemId)) {
                if (changeButton == null) {
                    //Do nothing
                } else if (changeButton.equals("Increase")) {
                    if (cart.containsKey(itemId)) {
                        int itemIdNum = Integer.parseInt(itemId);
                        int newQuantity = cart.get(itemId).getQuantityCart() + 1;
                        cart.updateCart(itemIdNum, newQuantity);
                    }
                } else if (changeButton.equals("Decrease")) {
                    if (cart.containsKey(itemId)) {
                        int itemIdNum = Integer.parseInt(itemId);
                        int newQuantity = cart.get(itemId).getQuantityCart() - 1;
                        if (newQuantity == 0) {
                            //Do nothing
                        } else {
                            cart.updateCart(itemIdNum, newQuantity);
                        }
                    }
                }
                //set attribute
                session.setAttribute("CART", cart);
            }
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
