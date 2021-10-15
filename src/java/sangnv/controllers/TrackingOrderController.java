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
import javax.servlet.http.HttpSession;
import sangnv.account.AccountDTO;
import sangnv.order.OrderDAO;
import sangnv.order.OrderDTO;
import sangnv.orderdetail.OrderDetailDAO;
import sangnv.orderdetail.OrderDetailDTO;
import sangnv.product.ProductDAO;
import sangnv.product.ProductDTO;
import sangnv.utils.MyConstants;
import sangnv.utils.ShoppingCart;

/**
 *
 * @author Shang
 */
public class TrackingOrderController extends HttpServlet {

    private final String TRACKING_PAGE = "trackingorder.jsp";
    private final String ADMIN_PAGE = "AdminSearchController";
    private final String HOME_PAGE = "SearchController";

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
        String url = HOME_PAGE;
        try {
            String txtSearch = request.getParameter("txtSearchOrder");
            HttpSession session = request.getSession();

            if (txtSearch == null) {
                txtSearch = "";
            }

            AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
            if (account != null && account.getRole() == MyConstants.ROLE_CUSTOMER) {
                url = TRACKING_PAGE;
                OrderDAO orderDAO = new OrderDAO();
                OrderDetailDAO detailDAO = new OrderDetailDAO();
                OrderDTO orderDto = orderDAO.getOrderByOrderIdOnAccount(txtSearch, account.getRole());
                if (orderDto != null) {
                    detailDAO.getOrderDetailsByOrderId(txtSearch);
                    List<OrderDetailDTO> detailList = detailDAO.getList();
                    if (detailList != null) {
                        ProductDAO productDAO = new ProductDAO();
                        ShoppingCart productList = new ShoppingCart();
                        for (OrderDetailDTO orderDetailDTO : detailList) {
                            ProductDTO tempDTO = productDAO.findOrderProductById(orderDetailDTO.getProductId());
                            if (tempDTO != null) {
                                tempDTO.setQuantityCart(orderDetailDTO.getQuantity());
                                tempDTO.setPrice(orderDetailDTO.getProductPrice());
                                productList.addCart(tempDTO);
                            }
                        }
                        request.setAttribute("ORDER", orderDto);
                        request.setAttribute("DETAILS", productList);
                    }
                } else {
                    request.setAttribute("MSG", "Order Not Found");
                }
            } else if (account != null && account.getRole() == MyConstants.ROLE_ADMIN) {
                url = ADMIN_PAGE;
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
