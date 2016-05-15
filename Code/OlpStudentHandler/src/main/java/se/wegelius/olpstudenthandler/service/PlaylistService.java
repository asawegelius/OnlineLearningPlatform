/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.LoggerFactory;
import se.wegelius.olpstudenthandler.dao.CourseDao;
import se.wegelius.olpstudenthandler.dao.PlaylistDao;
import se.wegelius.olpstudenthandler.dao.PlaylistDao;
import se.wegelius.olpstudenthandler.dao.UserDao;
import se.wegelius.olpstudenthandler.model.Playlist;
import se.wegelius.olpstudenthandler.model.persistance.CoursePersistance;
import se.wegelius.olpstudenthandler.model.persistance.PlaylistPersistance;
import se.wegelius.olpstudenthandler.model.persistance.PlaylistPersistance;
import se.wegelius.olpstudenthandler.model.persistance.UserPersistance;

/**
 *
 * @author asawe
 */
@Path("/playlist")
public class PlaylistService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);
    @Context
    private ServletContext sctx;          // dependency injection
    private static PlaylistDao dao;

    public PlaylistService() {
    }

    @GET
    @Path("/xml")
    @Produces({MediaType.APPLICATION_XML})
    public Response getXml() {
        checkContext();
        return Response.ok(dao, "application/xml").build();
    }

    @GET
    @Path("/xml/{id: \\d+}")
    @Produces({MediaType.APPLICATION_XML}) // could use "application/xml" instead
    public Response getXml(@PathParam("id") int id) {
        checkContext();
        return toRequestedType(id, "application/xml");
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json")
    public Response getJson() {
        checkContext();
        Set<PlaylistPersistance> set = dao.getAll();
        Set<Playlist> playlists = new HashSet<>();
        for (PlaylistPersistance p : set) {
            playlists.add(new Playlist(p));
        }
        return Response.ok(toJson(playlists), "application/json").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/{id: \\d+}")
    public Response getJson(@PathParam("id") int id) {
        checkContext();
        return toRequestedType(id, "application/json");
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/user/{id: \\d+}")
    public Response getJsonByPlaylist(@PathParam("id") int id) {
        checkContext();
        Map params = new HashMap();
        Set<Playlist> playlists = new HashSet<>();
        params.put("id", id);
        logger.info(id+"");
        Set<PlaylistPersistance> set = dao.query("SELECT p FROM PlaylistPersistance AS p WHERE p.user.userId = :id", params);
        if (set.iterator().hasNext()) {
            PlaylistPersistance p = set.iterator().next();
            playlists.add(new Playlist(dao.findByID(p.getPlaylistId())));
        }
        return Response.ok(toJson(playlists), "application/json").build();
    }

    @GET
    @Path("/plain")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain() {
        checkContext();
        Set<PlaylistPersistance> playlists = dao.getAll();
        String msg = dao.count() + "\n";
        for (PlaylistPersistance p : playlists) {
            msg = msg + p.getPlaylistId() + "; " + p.getCourse().getCourseId() + ", " + p.getUser().getUserId() + "\n";
        }
        return msg;
    }

    @GET
    @Path("/plain/{id: \\d+}")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain(@PathParam("id") int id) {
        checkContext();
        PlaylistPersistance playlist = dao.findByID(id);
        String msg = playlist.getPlaylistId() + ";" + playlist.getCourse().getCourseId() + ";" + playlist.getUser().getUserId();
        return msg;
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/create")
    public Response createJson(@QueryParam("course_id") int course_id, @QueryParam("user_id") int user_id) {
        checkContext();
        String msg = null;
        // Require both properties to create.
        if (course_id < 1) {
            msg = "course_id corrupted.\n";
        }
        if (user_id < 1) {
            if (msg == null) {
                msg = "user_id corrupted.\n";
            } else {
                msg = msg + ";user_id corrupted.\n";
            }
        }
        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            CoursePersistance course = new CourseDao().findByID(course_id);
            UserPersistance user = new UserDao().findByID(user_id);
            if (course == null) {
                msg = "course missing.\n";
            }
            if (user == null) {
                if (msg == null) {
                    msg = "user missing.\n";
                } else {
                    msg = msg + ";user missing.\n";
                }
            }
            if (msg != null) {
                return Response.status(Response.Status.BAD_REQUEST).
                        entity(msg).
                        type(MediaType.APPLICATION_JSON).
                        build();
            } else {
                // Otherwise, create the Playlist and add it to the database.
                PlaylistPersistance playlist = new PlaylistPersistance();
                playlist.setCourse(course);
                playlist.setUser(user);
                dao.save(playlist);
                return Response.ok(toJson(new Playlist(playlist)), MediaType.APPLICATION_JSON).build();
            }
        }
    }

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/create")
    public Response createPlain(@QueryParam("course_id") int course_id, @QueryParam("user_id") int user_id) {
        checkContext();
        String msg = null;
        // Require both properties to create.
        if (course_id < 1) {
            msg = "course_id corrupted.\n";
        }
        if (user_id < 1) {
            if (msg == null) {
                msg = "user_id corrupted.\n";
            } else {
                msg = msg + ";user_id corrupted.\n";
            }
        }
        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        } else {
            CoursePersistance course = new CourseDao().findByID(course_id);
            UserPersistance user = new UserDao().findByID(user_id);
            if (course == null) {
                msg = "course missing.\n";
            }
            if (user == null) {
                if (msg == null) {
                    msg = "user missing.\n";
                } else {
                    msg = msg + ";user missing.\n";
                }
            }
            if (msg != null) {
                return Response.status(Response.Status.BAD_REQUEST).
                        entity(msg).
                        type(MediaType.TEXT_PLAIN).
                        build();
            } else {
                // Otherwise, create the Playlist and add it to the database.
                PlaylistPersistance playlist = new PlaylistPersistance();
                playlist.setCourse(course);
                playlist.setUser(user);
                dao.save(playlist);
                msg = playlist.getPlaylistId()+ ";" + playlist.getCourse().getCourseId() + ";" + playlist.getUser().getUserId();
                return Response.ok(msg, "text/plain").build();
            }
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("json/update/{id: \\d+}")
    public Response updateJson(@QueryParam("id") int id,
            @QueryParam("course_id") int course_id, @QueryParam("user_id") int user_id) {
        checkContext();
        String msg = null;
        // Require both properties to create.
        if (course_id < 1) {
            msg = "course_id corrupted.\n";
        }
        if (user_id < 1) {
            if (msg == null) {
                msg = "user_id corrupted.\n";
            } else {
                msg = msg + ";user_id corrupted.\n";
            }
        }
        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            CoursePersistance course = new CourseDao().findByID(course_id);
            UserPersistance user = new UserDao().findByID(user_id);
            if (course == null) {
                msg = "course missing.\n";
            }
            if (user == null) {
                if (msg == null) {
                    msg = "user missing.\n";
                } else {
                    msg = msg + ";user missing.\n";
                }
            }
            if (msg != null) {
                return Response.status(Response.Status.BAD_REQUEST).
                        entity(msg).
                        type(MediaType.APPLICATION_JSON).
                        build();
            } else {
                // Update.
                PlaylistPersistance playlist = dao.findByID(id);
                if (playlist == null) {
                    msg = "user missing";
                    return Response.status(Response.Status.BAD_REQUEST).
                            entity(msg).
                            type(MediaType.APPLICATION_JSON).
                            build();
                } else {
                    playlist.setCourse(course);
                    playlist.setUser(user);
                    dao.update(playlist);
                    return Response.ok(toJson(new Playlist(playlist)), MediaType.APPLICATION_JSON).build();
                }
            }
        }
    }

    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("plain/update/{id: \\d+}")
    public Response update(@QueryParam("id") int id,
            @QueryParam("course_id") int course_id, @QueryParam("user_id") int user_id) {
        checkContext();
        String msg = null;
        // Require both properties to create.
        if (course_id < 1) {
            msg = "course_id corrupted.\n";
        }
        if (user_id < 1) {
            if (msg == null) {
                msg = "user_id corrupted.\n";
            } else {
                msg = msg + ";user_id corrupted.\n";
            }
        }
        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        } else {
            CoursePersistance course = new CourseDao().findByID(course_id);
            UserPersistance user = new UserDao().findByID(user_id);
            if (course == null) {
                msg = "course missing.\n";
            }
            if (user == null) {
                if (msg == null) {
                    msg = "user missing.\n";
                } else {
                    msg = msg + ";user missing.\n";
                }
            }
            if (msg != null) {
                return Response.status(Response.Status.BAD_REQUEST).
                        entity(msg).
                        type(MediaType.TEXT_PLAIN).
                        build();
            } else {
                // Update.
                PlaylistPersistance playlist = dao.findByID(id);
                if (playlist == null) {
                    msg = "user missing";
                    return Response.status(Response.Status.BAD_REQUEST).
                            entity(msg).
                            type(MediaType.APPLICATION_JSON).
                            build();
                } else {
                    playlist.setCourse(course);
                    playlist.setUser(user);
                    dao.update(playlist);
                    msg = playlist.getPlaylistId()+ ";" + playlist.getCourse().getCourseId() + ";" + playlist.getUser().getUserId();
                    return Response.ok(msg, "text/plain").build();
                }
            }
        }
    }
    
    

    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/delete/{id: \\d+}")
    public Response delete(@QueryParam("id") int id
    ) {
        checkContext();
        String msg = null;
        PlaylistPersistance playlist = dao.findByID(id);
        if (playlist == null) {
            msg = "user missing.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        dao.delete(playlist);
        msg = "Playlist deleted;" + id + "\n";

        return Response.ok(msg, "text/plain").build();
    }
    
    //** utilities

    private void checkContext() {
        if (dao == null) {
            dao = new PlaylistDao();
        }
    }

    // PlaylistPersistance --> JSON document
    private String toJson(Playlist playlist) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(playlist);
        } catch (Exception e) {
        }
        return json;
    }

    // PlaylistPersistance set --> JSON document
    private String toJson(Set<Playlist> playlistSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(playlistSet);
        } catch (Exception e) {
        }
        return json;
    }

    // Generate an HTTP error response or typed OK response.
    private Response toRequestedType(int id, String type) {
        PlaylistPersistance playlist = dao.findByID(id);
        if (playlist == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else if (type.contains("json")) {
            return Response.ok(toJson(new Playlist(playlist)), type).build();
        } else {
            return Response.ok(new Playlist(playlist), type).build(); // toXml is automatic
        }
    }
}
