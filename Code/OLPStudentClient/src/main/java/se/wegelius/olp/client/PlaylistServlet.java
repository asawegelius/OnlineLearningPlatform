/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.client;

import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.LoggerFactory;
import se.wegelius.olp.model.Playlist;

/**
 *
 * @author asawe
 */
public class PlaylistServlet extends HttpServlet {
    
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
        String courseid = request.getParameter("courseid");
        String userid = request.getParameter("userid");
        logger.info("userid : " + userid + " courseid : " + courseid);
        
        PlaylistClient playlist = new PlaylistClient();
        ClientResponse playlistResponse = playlist.createJson(Integer.parseInt(userid), Integer.parseInt(courseid));
        logger.info("playlistResponse = " + playlistResponse);
        String jsonPlaylist = playlistResponse.getEntity(String.class);
        logger.info("jsonPlaylist = " + jsonPlaylist);
        Playlist p = new Gson().fromJson(jsonPlaylist, Playlist.class);
        logger.info("the new playlist: " + p.getPlaylistId()+ ", " + p.getCourseId()+ ", " + p.getUserId());
        response.sendRedirect("/OLP/course.jsp");
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
