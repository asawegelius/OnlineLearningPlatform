/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.servlet;

import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import se.wegelius.olp.client.UserClient;
import se.wegelius.olp.model.Encryptor;
import se.wegelius.olp.model.User;

/**
 *
 * @author asawe
 */
public class LoginServlet extends HttpServlet {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoginServlet.class);

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
        // get request parameters for userID and password
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        logger.info("email = " + email + " password = " + password);
        // generate encrypted password
        String encryptedPassword = Encryptor.get_SHA_512_SecurePassword(password);

        UserClient userClient = new UserClient();
        ClientResponse userResponse = userClient.getJsonUser(email);
        switch (userResponse.getStatusInfo().getReasonPhrase()) {
            case "OK":
                String jsonUser = userResponse.getEntity(String.class);
                User user = new Gson().fromJson(jsonUser, User.class);
                logger.info("the user: " + user.getPassword() + ", " + user.getUserName() + ", " + user.getUserId());
                if (user.getPassword().equals(encryptedPassword)) {
                    logger.info("correct password");

                    HttpSession session = request.getSession();
                    session.setAttribute("user", email);
                    session.setAttribute("userId", user.getUserId());
                    //setting session to expiry in 30 mins
                    session.setMaxInactiveInterval(30 * 60);
                    Cookie userName = new Cookie("user", email);
                    response.addCookie(userName);
                    //Get the encoded URL string
                    String referer = request.getHeader("Referer");
                    logger.info("Referer = " + referer);
                    if (referer == null) {
                        referer = "index.jsp";
                    }
                    String encodedURL = response.encodeRedirectURL(referer);
                    response.sendRedirect(encodedURL);

                } else {
                    logger.info("wrong password");
                    HttpSession session = request.getSession();
                    session.setAttribute("user", null);
                    //setting session to expiry in 30 mins
                    session.setMaxInactiveInterval(30 * 60);
                    session.setAttribute("msg", "wrong password");
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            if (cookie.getName().equals("user")) {
                                logger.info("user = " + cookie.getValue());
                            }
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                    //Get the encoded URL string
                    String referer = request.getHeader("Referer");
                    logger.info("Referer = " + referer);
                    if (referer == null) {
                        referer = "index.jsp";
                    }
                    String encodedURL = response.encodeRedirectURL(referer);
                    response.sendRedirect(encodedURL);

                }
                break;

            case "Bad Request": {
                String msg = userResponse.getEntity(String.class);
                logger.error("error message after trying to find user: " + msg);
                if (msg.startsWith("-1")) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", null);
                    //setting session to expiry in 30 mins
                    session.setMaxInactiveInterval(30 * 60);
                    session.setAttribute("msg", "wrong email");
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            if (cookie.getName().equals("user")) {
                                logger.info("user = " + cookie.getValue());
                            }
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                    //Get the encoded URL string
                    String referer = request.getHeader("Referer");
                    logger.info("Referer = " + referer);
                    if (referer == null) {
                        referer = "index.jsp";
                    }
                    String encodedURL = response.encodeRedirectURL(referer);
                    response.sendRedirect(encodedURL);

                }
                break;
            }
            default: {
                String msg = userResponse.getEntity(String.class);
                logger.error("error message after trying to find user: " + msg);
                HttpSession session = request.getSession();
                session.setAttribute("user", null);
                //setting session to expiry in 30 mins
                session.setMaxInactiveInterval(30 * 60);
                session.setAttribute("msg", msg);
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("user")) {
                            logger.info("user = " + cookie.getValue());
                        }
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
                //Get the encoded URL string
                String referer = request.getHeader("Referer");
                logger.info("Referer = " + referer);
                if (referer == null) {
                    referer = "index.jsp";
                }
                String encodedURL = response.encodeRedirectURL(referer);
                response.sendRedirect(encodedURL);

                break;
            }
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
