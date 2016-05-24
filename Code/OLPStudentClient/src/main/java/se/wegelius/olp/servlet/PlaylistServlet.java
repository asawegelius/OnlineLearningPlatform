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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.LoggerFactory;
import se.wegelius.olp.client.PlaylistClient;
import se.wegelius.olp.model.Playlist;

/**
 *
 * @author asawe
 */
public class PlaylistServlet extends HttpServlet {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PlaylistServlet.class);

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
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equalsIgnoreCase("delete")) {
                doDelete(request, response);
                return;
            }
        }

        logger.info("userid : " + userid + " courseid : " + courseid);

        PlaylistClient playlist = new PlaylistClient();
        MultivaluedMap params = playlist.getParameters(Integer.parseInt(courseid), Integer.parseInt(userid));
        ClientResponse playlistResponse = playlist.createJson(params);
        logger.info("playlistResponse = " + playlistResponse);
        String jsonPlaylist = playlistResponse.getEntity(String.class);
        logger.info("jsonPlaylist = " + jsonPlaylist);
        Playlist p = new Gson().fromJson(jsonPlaylist, Playlist.class);
        logger.info("the new playlist: " + p.getPlaylistId() + ", " + p.getCourseId() + ", " + p.getUserId());
        response.sendRedirect("/OLP/course.jsp");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String playlistid = request.getParameter("playlistid");
        logger.info("playlistid : " + playlistid);
        PlaylistClient playlist = new PlaylistClient();
        ClientResponse playlistResponse = playlist.delete(playlistid);
        logger.info("playlistResponse = " + playlistResponse);
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
