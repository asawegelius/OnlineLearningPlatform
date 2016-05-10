/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.LoggerFactory;
import se.wegelius.olp.model.VerificationToken;

/**
 *
 * @author asawe
 */
public class OLPRegister extends HttpServlet {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OLPRegister.class);

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
        String t = request.getParameter("go");
        logger.info("the token: " + t);
        // get the token
        VerificationTokenClient tokenClient = new VerificationTokenClient();
        ClientResponse tokenResponse = tokenClient.getJsonToken(t);
        String jsonToken = tokenResponse.getEntity(String.class);
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values 
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement je, java.lang.reflect.Type type, JsonDeserializationContext jdc) throws JsonParseException {
                return new Date(je.getAsJsonPrimitive().getAsLong());
            }
        });

        Gson gson = builder.create();
        VerificationToken token = gson.fromJson(jsonToken, VerificationToken.class);
        logger.info(jsonToken);
        //update the user to enabled = true;
        UserClient userClient = new UserClient();
        MultivaluedMap queryParam = userClient.getParameters(token.getUser().getUserId(), null,  token.getUser().getEmail(), token.getUser().getPassword(), 1);
        userClient.updateJson(queryParam, token.getUser().getUserId());
        //redirect to index with user logged in
        HttpSession session = request.getSession();
        session.removeAttribute("msg");
        session.setAttribute("user", token.getUser().getEmail());
        session.setMaxInactiveInterval(30 * 60);
        Cookie userName = new Cookie("user", token.getUser().getEmail());
        userName.setMaxAge(30 * 60);
        response.addCookie(userName);
        String encodedURL = response.encodeRedirectURL("index.jsp");
        response.sendRedirect(encodedURL);
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
