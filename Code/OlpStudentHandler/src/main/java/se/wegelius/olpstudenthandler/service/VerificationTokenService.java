/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
import org.slf4j.LoggerFactory;
import se.wegelius.olpstudenthandler.dao.UserDao;
import se.wegelius.olpstudenthandler.dao.VerificationTokenDao;
import se.wegelius.olpstudenthandler.model.VerificationToken;
import se.wegelius.olpstudenthandler.model.persistance.UserPersistance;
import se.wegelius.olpstudenthandler.model.persistance.VerificationtokenPersistance;

/**
 *
 * @author asawe
 */
@Path("/token")
public class VerificationTokenService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(VerificationTokenService.class);
    @Context
    private ServletContext sctx;          // dependency injection
    private static VerificationTokenDao dao;

    public VerificationTokenService() {
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
        Set<VerificationtokenPersistance> set = dao.getAll();
        Set<VerificationToken> tokens = new HashSet<>();
        for (VerificationtokenPersistance p : set) {
            tokens.add(new VerificationToken(p));
        }
        return Response.ok(toJson(tokens), "application/json").build();
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
    @Path("/json/token/{token: \\d+}")
    public Response getJsonToken(@PathParam("token") String token) {
        checkContext();
        int id = -1;
        Map params = new HashMap();
        params.put("token", token);
        Set<VerificationtokenPersistance> tokens = dao.query("SELECT t FROM VerificationtokenPersistance AS t WHERE t.token = :token", params);        
        if (tokens.iterator().hasNext()) {
            VerificationtokenPersistance t = tokens.iterator().next();
            id = t.getVerificationtokenId();
        }
        return toRequestedType( id, "application/json");
    }

    @GET
    @Path("/plain")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain() {
        checkContext();
        Set<VerificationtokenPersistance> tokens = dao.getAll();
        String msg = dao.count() + "\n";
        for (VerificationtokenPersistance token : tokens) {
            msg = msg + token.getVerificationtokenId() + ";" + token.getToken() + ";" + token.getUser().getUserName() + ";" + token.getExpiryDate() + "\n";
        }
        return msg;
    }

    @GET
    @Path("/plain/{id: \\d+}")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain(@PathParam("id") int id) {
        checkContext();
        VerificationtokenPersistance token = dao.findByID(id);
        String msg = token.getVerificationtokenId() + ";" + token.getToken() + ";" + token.getUser().getUserName() + ";" + token.getExpiryDate() + "\n";
        return msg;
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/create")
    @Consumes("application/octet-stream")
    public Response createJson(@QueryParam("user_id") int user_id,
            @QueryParam("token") String token) {
        checkContext();
        // Require all properties to create.
        String msg = "";
        if (user_id < 1) {
            msg = "user_id corrupted";
        }
        if (token == null) {
            msg = msg + ";token missing";
        }
        if (!msg.equals("")) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        // get the vtokens user
        UserPersistance user = new UserDao().findByID(user_id);
        if (user == null) {
            msg = msg + "user missing";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        // check if the user already have a token and set a new date if it exists since a user can have at the most one token
        Map params = new HashMap();
        params.put("user", user);
        VerificationtokenPersistance t;
        Set<VerificationtokenPersistance> tokens = dao.query("SELECT t FROM VerificationtokenPersistance AS t WHERE t.user = :user", params);
        if (tokens.iterator().hasNext()) {
            t = tokens.iterator().next();
            t.setUser(user);
            t.setExpiryDate(new VerificationToken(t).getExpiryDate());
            dao.update(t);
        } // Otherwise, create the VerificationTokenPersistance and add it to the database.
        else {
            t = new VerificationtokenPersistance();
            t.setUser(user);
            t.setToken(token);
            t.setExpiryDate(new VerificationToken(t).getExpiryDate());
            
            dao.save(t);
        }
        return Response.ok(toJson(new VerificationToken(t)), MediaType.APPLICATION_JSON).build();

    }

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/create")
    public Response createPlain(@QueryParam("user_id") int user_id,
            @QueryParam("token") String token) {
        checkContext();
        // Require all properties to create.
        String msg = "";
        if (user_id < 1) {
            msg = "user_id corrupted";
        }
        if (token == null) {
            msg = msg + ";token missing";
        }
        if (!msg.equals("")) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        // get the vtokens user
        UserPersistance user = new UserDao().findByID(user_id);
        if (user == null) {
            msg = msg + "user missing";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        // check if the user already have a token and set a new date if it exists since a user can have at the most one token
        Map params = new HashMap();
        params.put("user_id", user.getUserId());
        VerificationtokenPersistance t;
        Set<VerificationtokenPersistance> tokens = dao.query("SELECT t FROM VerificationtokenPersistance AS t WHERE t.user.userId = :user_id", params);
        if (tokens.iterator().hasNext()) {
            t = tokens.iterator().next();
            t.setExpiryDate(new VerificationToken(t).getExpiryDate());
            dao.update(t);
        } // Otherwise, create the VerificationTokenPersistance and add it to the database.
        else {
            t = new VerificationtokenPersistance();
            t.setUser(user);
            t.setToken(token);
            t.setExpiryDate(new VerificationToken(t).getExpiryDate());
            dao.save(t);
        }
        msg = t.getVerificationtokenId() + ";" + token;
        return Response.ok(msg, "text/plain").build();

    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("json/update/{id: \\d+}")
    public Response updateJson(@QueryParam("id") int id, @QueryParam("user_id") int user_id,
            @QueryParam("token") String token, @QueryParam("expiry_day") Date expiry_day) {
        checkContext();
        VerificationtokenPersistance vtoken = dao.findByID(id);
        UserPersistance user = new UserDao().findByID(user_id);
        // Check that sufficient data are present to do an edit.
        String msg = null;
        if (user_id == 0) {
            msg = "Property user_id is not given.\n";
        }
        if (token == null) {
            msg = msg + "Property password is not given.\n";
        }
        if (expiry_day == null) {
            msg = msg + "Property 'expiry_day' is not given.\n";
        }
        if (msg != null) {
            msg = msg + "Missing data, can't edit.\n";
        }
        if (vtoken == null || user == null) {
            msg = msg + "There is no verificationtoken with id " + id + " or user with id " + user_id + "\n";
        } else {
            vtoken.setExpiryDate(expiry_day);
            vtoken.setToken(token);
            vtoken.setUser(user);
        }

        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        dao.update(vtoken);

        return Response.ok(toJson(new VerificationToken(vtoken)), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("plain/update/{id: \\d+}")
    public Response update(@QueryParam("id") int id, @QueryParam("user_id") int user_id,
            @QueryParam("token") String token, @QueryParam("expiry_day") Date expiry_day) {
        checkContext();
        VerificationtokenPersistance vtoken = dao.findByID(id);
        UserPersistance user = new UserDao().findByID(user_id);
        // Check that sufficient data are present to do an edit.
        String msg = null;
        if (user_id == 0) {
            msg = "Property user_id is not given.\n";
        }
        if (token == null) {
            msg = msg + "Property password is not given.\n";
        }
        if (expiry_day == null) {
            msg = msg + "Property 'expiry_day' is not given.\n";
        }
        if (msg != null) {
            msg = msg + "Missing data, can't edit.\n";
        }
        if (vtoken == null || user == null) {
            msg = msg + "There is no verificationtoken with id " + id + " or user with id " + user_id + "\n";
        } else {
            vtoken.setExpiryDate(expiry_day);
            vtoken.setToken(token);
            vtoken.setUser(user);
        }
        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        dao.update(vtoken);
        msg = id + ";" + token + "\n";
        return Response.ok(msg, "text/plain").build();
    }

    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        checkContext();
        VerificationtokenPersistance user = dao.findByID(id);
        if (user == null) {
            String msg = "There is no branch with id " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        } else {
            dao.delete(user);
            String msg = "VerificationToken " + id + " deleted.\n";
            return Response.ok(msg, "text/plain").build();
        }
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new VerificationTokenDao();
        }
    }

    // VerificationToken --> JSON document
    private String toJson(VerificationToken token) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(token);
        } catch (Exception e) {
        }
        return json;
    }

    // VerificationToken set --> JSON document
    private String toJson(Set<VerificationToken> userSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(userSet);
        } catch (Exception e) {
        }
        return json;
    }

    // Generate an HTTP error response or typed OK response.
    private Response toRequestedType(int id, String type) {
        VerificationtokenPersistance token = dao.findByID(id);
        if (token == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else if (type.contains("json")) {
            return Response.ok(toJson(new VerificationToken(token)), type).build();
        } else {
            return Response.ok(new VerificationToken(token), type).build(); // toXml is automatic
        }
    }
}
