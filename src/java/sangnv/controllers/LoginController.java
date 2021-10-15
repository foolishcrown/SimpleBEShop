/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sangnv.account.AccountDAO;
import sangnv.account.AccountDTO;
import sangnv.utils.MyConstants;

/**
 *
 * @author Shang
 */
public class LoginController extends HttpServlet {

    private final String MAIN_PAGE = "SearchController";
    private final String ADMIN_PAGE = "AdminSearchController";
    private final String ERROR_PAGE = "errorpage.jsp";

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
        String url = ERROR_PAGE;
        String userid = request.getParameter("userId");
        String password = request.getParameter("password");
        AccountDAO dao = new AccountDAO();
        try {
            HttpSession session = request.getSession();
            AccountDTO authenAccount = (AccountDTO) session.getAttribute("ACCOUNT");
            if (authenAccount == null) {
                if (userid != null && password != null) {
                    authenAccount = dao.authenticationAccount(userid, password);
                    if (authenAccount != null) {
                        session.setAttribute("ACCOUNT", authenAccount);
                        if (authenAccount.getRole() == MyConstants.ROLE_CUSTOMER) {
                            url = MAIN_PAGE;
                        } else if (authenAccount.getRole() == MyConstants.ROLE_ADMIN) {
                            url = ADMIN_PAGE;
                        }
                    } else {
                        request.setAttribute("ERRORLOGIN", "Username or password is wrong, please go back and try again!");
                    }
                }
            } else {
                url = MAIN_PAGE;
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
