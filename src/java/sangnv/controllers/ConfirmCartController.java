/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sangnv.account.AccountDTO;
import sangnv.product.ProductDAO;
import sangnv.product.ProductDTO;
import sangnv.utils.RandomId;
import sangnv.utils.ShoppingCart;

/**
 *
 * @author Shang
 */
public class ConfirmCartController extends HttpServlet {

    private final String MAIN_PAGE = "SearchController";
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = CART_PAGE;
        String[] checkedIds;
        String accountName = null;
        String accountPhone = null;
        String accountAddress = null;
        System.out.println("GOOO");
        //Get Session
        HttpSession session = request.getSession();
        //Get session request
        ShoppingCart cart = (ShoppingCart) session.getAttribute("CART");
        AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
        //New cart
        if (cart == null) {
            cart = new ShoppingCart();
        }

        if (account == null) {
            accountName = request.getParameter("accountName");
            accountPhone = request.getParameter("accountPhone");
            accountAddress = request.getParameter("accountAddress");
        }

        checkedIds = request.getParameterValues("checkedItems");
        //Get update type
        ProductDAO dao = new ProductDAO();
        try {
            List<ProductDTO> detailList = new ArrayList<>();
            if (checkedIds != null) {
                //Check stock in product store
                boolean checkStock = true;
                for (String checkedId : checkedIds) {
                    if (cart.keySet().contains(checkedId)) {
                        detailList.add(cart.get(checkedId));
                        int idNum = Integer.parseInt(checkedId);
                        if (!dao.checkQuantity(idNum, cart.get(checkedId).getQuantityCart())) {
                            checkStock = false;
                        }
                    }
                }
                if (checkStock) {
                    String orderId = RandomId.generateId();
                    //Confirm guest order
                    boolean checkCreateOrder;
                    if (account != null) {
                        checkCreateOrder = dao.createOrderAccount(orderId, account);
                    } else if (accountAddress != null && accountName != null && accountPhone != null) {
                        checkCreateOrder = dao.createOrderGuest(orderId, new String(accountName.getBytes("iso-8859-1"), "utf-8"), accountPhone, new String(accountAddress.getBytes("iso-8859-1"), "utf-8"));
                    } else {
                        checkCreateOrder = false;
                    }
                    if (checkCreateOrder) {
                        checkCreateOrder = dao.updateStock(detailList);
                        if (checkCreateOrder) {
                            checkCreateOrder = dao.createDetail(detailList, orderId);
                            if (checkCreateOrder) {
                                for (String checkedId : checkedIds) {
                                    if (cart.keySet().contains(checkedId)) {
                                        int checkedIdNum = Integer.parseInt(checkedId);
                                        cart.removeCart(checkedIdNum);
                                    }
                                }
                                url = MAIN_PAGE;
                            }
                        }
                    }
                } else {
                    request.setAttribute("MsgError", "Some item on your cart is currently out of stock! Please check and try again!");
                }
            }
        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (IOException | NumberFormatException e) {
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
