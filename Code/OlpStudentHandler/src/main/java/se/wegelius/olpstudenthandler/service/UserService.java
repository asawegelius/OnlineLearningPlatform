/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.HttpURLConnection;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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
        for (UserPersistance b : users) {
            msg = msg + b.getUserId() + ";" + b.getUserName() + ";" + b.getPassword() + "\n";
        }
        return msg;
    }

    @GET
    @Path("/plain/{id: \\d+}")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain(@PathParam("id") int id) {
        checkContext();
        UserPersistance user = dao.findByID(id);
        String msg = user.getUserId() + ";" + user.getUserName() + ";" + user.getPassword();
        return msg;
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/create")
    @Consumes("application/octet-stream")
    public Response createJson(@QueryParam("user_name") String user_name,
            @QueryParam("password") String pw) {
        checkContext();
        String msg = null;
        // Require both properties to create.
        if (user_name == null || pw == null) {
            msg = "Property 'user_name' or property password is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        if (msg != null) {
            // Otherwise, create the UserPersistance and add it to the database.
            UserPersistance user = new UserPersistance();
            user.setUserName(user_name);

            byte[] password = pw.getBytes();
            user.setPassword(password);
            dao.save(user);
            return Response.ok(toJson(new User(user)), MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                    .entity(msg)
                    .build();
        }
    }

  /*  
    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/create")
    public Response createPlain(@QueryParam("user_name") String user_name,
            @QueryParam("password") byte[] password) {
        checkContext();
        // Require both properties to create.
        if (user_name == null || password == null) {
            String msg = "Property 'user_name' or property password is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        } else {
        // Otherwise, create the Mail and add it to the database.
        UserPersistance user = new UserPersistance();
        user.setUserName(user_name);
        user.setPassword(password);
        dao.save(user);
        int id = user.getUserId();
        String msg = id + ";" + user_name + "\n";
        return Response.ok(msg, "text/plain").build();
        }
    }
*/
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("json/update/{id: \\d+}")
    public Response updateJson(@PathParam("id") int id,
            @QueryParam("branch_name") String branch_name) {
        checkContext();

        System.out.println("in put json got request for id " + id);
        UserPersistance user = dao.findByID(id);
        // Check that sufficient data are present to do an edit.
        String msg = null;
        if (branch_name == null) {
            msg = "branch_name is not given: nothing to edit\n";
        } else if (user == null) {
            msg = "There is no branch with id " + id + "\n";
        }

        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            // Update.
            user.setUserName(branch_name);
        }
        dao.update(user);

        return Response.ok(toJson(new User(user)), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("plain/update/{id: \\d+}")
    public Response update(@FormParam("id") int id,
            @QueryParam("user_name") String user_name,
            @QueryParam("password") String pw) {
        checkContext();

        // Check that sufficient data are present to do an edit.
        String msg = null;
        if (user_name == null) {
            msg = "user_name is not given: nothing to edit\n";
        }
        if (pw == null) {
            msg = msg + "password is not given: nothing to edit\n";
        }
        UserPersistance user = dao.findByID(id);
        if (user == null) {
            msg = "There is no user with id " + id + "\n";
        } else {
            user.setUserName(user_name);
            user.setPassword(pw.getBytes());
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
