/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
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
import se.wegelius.olpstudenthandler.dao.UserDao;
import se.wegelius.olpstudenthandler.model.User;
import se.wegelius.olpstudenthandler.model.persistance.UserPersistance;

/**
 *
 * @author asawe
 */
@Path("/user")
public class UserService {

    @Context
    private ServletContext sctx;          // dependency injection
    private static UserDao dao;

    public UserService() {
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
        Set<UserPersistance> set = dao.getAll();
        Set<User> users = new HashSet<>();
        for (UserPersistance p : set) {
            users.add(new User(p));
        }
        return Response.ok(toJson(users), "application/json").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/{id: \\d+}")
    public Response getJson(@PathParam("id") int id) {
        checkContext();
        return toRequestedType(id, "application/json");
    }

    @GET
    @Path("/plain")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain() {
        checkContext();
        Set<UserPersistance> users = dao.getAll();
        String msg = dao.count() + "\n";
        for (UserPersistance user : users) {
            msg = msg + user.getUserId() + ";" + user.getUserName() + ";" + user.getPassword() + ";" + user.isEnabled() + "\n";
        }
        return msg;
    }

    @GET
    @Path("/plain/{id: \\d+}")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain(@PathParam("id") int id) {
        checkContext();
        UserPersistance user = dao.findByID(id);
        String msg = user.getUserId() + ";" + user.getUserName() + ";" + user.getPassword() + ";" + user.isEnabled();
        return msg;
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/create")
    @Consumes("application/octet-stream")
    public Response createJson(@QueryParam("user_name") String user_name,
            @QueryParam("password") String password, @QueryParam("enabled") int enabled) {
        checkContext();
        // Require all properties to create.
        if (user_name == null || password == null || enabled < 0) {
            String msg = "Property 'user_name', property 'password' or property 'enabled' is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }

        // check the user_name is unique
        Set<UserPersistance> users = dao.getAll(" WHERE `user`.`user_name` = " + user_name);
        if (users != null) {
            // the user is activated 
            if (users.iterator().next().isEnabled()) {
                String msg = "Property 'user_name' is not unique.\n";
                return Response.status(Response.Status.BAD_REQUEST).
                        entity(msg).
                        type(MediaType.APPLICATION_JSON).
                        build();
            } // the user is not activated
            else {
                String msg = "Property 'user_name' is not activated.\n";
                return Response.status(Response.Status.BAD_REQUEST).
                        entity(msg).
                        type(MediaType.APPLICATION_JSON).
                        build();
            }
        }
        // Otherwise, create the UserPersistance and add it to the database.
        UserPersistance user = new UserPersistance();
        user.setUserName(user_name);
        user.setPassword(password);
        if (enabled == 0) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        dao.save(user);
        return Response.ok(toJson(new User(user)), MediaType.APPLICATION_JSON).build();

    }

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/create")
    public Response createPlain(@QueryParam("user_name") String user_name,
            @QueryParam("password") String password, @QueryParam("enabled") int enabled) {
        checkContext();
        // Require all properties to create.
        if (user_name == null || password == null || enabled < 0) {
            String msg = "Property 'user_name', property 'password' or property 'enabled' is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        } else {
            // check the user_name is unique
            Set<UserPersistance> users = dao.getAll(" WHERE `user`.`user_name` = " + user_name);
            if (users != null) {
                String msg = "Property 'user_name' is not unique.\n";
                return Response.status(Response.Status.BAD_REQUEST).
                        entity(msg).
                        type(MediaType.TEXT_PLAIN).
                        build();
            }
            // Otherwise, create the Mail and add it to the database.
            UserPersistance user = new UserPersistance();
            user.setUserName(user_name);
            user.setPassword(password);
            if (enabled == 0) {
                user.setEnabled(false);
            } else {
                user.setEnabled(true);
            }
            dao.save(user);
            int id = user.getUserId();
            String msg = id + ";" + user_name + "\n";
            return Response.ok(msg, "text/plain").build();
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("json/update/{id: \\d+}")
    public Response updateJson(@QueryParam("id") int id, @QueryParam("user_name") String user_name,
            @QueryParam("password") String password, @QueryParam("enabled") int enabled) {
        checkContext();

        System.out.println("in put json got request for id " + id);
        UserPersistance user = dao.findByID(id);
        // Check that sufficient data are present to do an edit.
        String msg = null;
        if (user_name == null) {
            msg = "Property user_name is not given.\n";
        }
        if (password == null) {
            msg = msg + "Property password is not given.\n";
        }
        if (enabled < 0) {
            msg = msg + "Property 'enabled' is corrupted.\n";
        }
        if (msg != null) {
            msg = msg + "Nothing to edit.\n";
        }
        if (user == null) {
            msg = msg + "There is no user with id " + id + "\n";
        } else {
            user.setUserName(user_name);
            user.setPassword(password);
            if (enabled == 0) {
                user.setEnabled(false);
            } else {
                user.setEnabled(true);
            }
        }

        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        dao.update(user);

        return Response.ok(toJson(new User(user)), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("plain/update/{id: \\d+}")
    public Response update(@QueryParam("id") int id, @QueryParam("user_name") String user_name,
            @QueryParam("password") String password, @QueryParam("enabled") int enabled) {
        checkContext();
        // Check that sufficient data are present to do an edit.
        String msg = null;
        UserPersistance user = dao.findByID(id);
        if (user_name == null) {
            msg = "Property user_name is not given.\n";
        }
        if (password == null) {
            msg = msg + "Property password is not given.\n";
        }
        if (enabled < 0) {
            msg = msg + "Property 'enabled' is corrupted.\n";
        }
        if (msg != null) {
            msg = msg + "Nothing to edit.\n";
        }
        if (user == null) {
            msg = msg + "There is no user with id " + id + "\n";
        } else {
            user.setUserName(user_name);
            user.setPassword(password);
            if (enabled == 0) {
                user.setEnabled(false);
            } else {
                user.setEnabled(true);
            }
        }
        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        dao.update(user);
        msg = id + ";" + user_name + "\n";
        return Response.ok(msg, "text/plain").build();
    }

    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        checkContext();
        UserPersistance user = dao.findByID(id);
        if (user == null) {
            String msg = "There is no branch with id " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        } else {
            dao.delete(user);
            String msg = "User " + id + " deleted.\n";
            return Response.ok(msg, "text/plain").build();
        }
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new UserDao();
        }
    }

    // CourseBranchPersistance --> JSON document
    private String toJson(User user) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
        }
        return json;
    }

    // UserPersistance set --> JSON document
    private String toJson(Set<User> userSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(userSet);
        } catch (Exception e) {
        }
        return json;
    }

    // Generate an HTTP error response or typed OK response.
    private Response toRequestedType(int id, String type) {
        UserPersistance user = dao.findByID(id);
        if (user == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else if (type.contains("json")) {
            return Response.ok(toJson(new User(user)), type).build();
        } else {
            return Response.ok(new User(user), type).build(); // toXml is automatic
        }
    }
}