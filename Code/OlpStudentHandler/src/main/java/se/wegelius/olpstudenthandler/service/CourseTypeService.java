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
import se.wegelius.olpstudenthandler.dao.CourseTypeDao;
import se.wegelius.olpstudenthandler.model.CourseType;
import se.wegelius.olpstudenthandler.model.persistance.CourseTypePersistance;

/**
 *
 * @author Kellogs
 */
@Path("/type")
public class CourseTypeService {
    
    @Context
    private ServletContext sctx;          // dependency injection
    private static CourseTypeDao dao;

    public CourseTypeService() {
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
    public Response getXml(
            @PathParam("id") int id
    ) {
        checkContext();
        return toRequestedType(id, "application/xml");
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json")
    public Response getJson() {
        checkContext();
        Set<CourseTypePersistance> set = dao.getAll();
        Set<CourseType> typees = new HashSet<>();
        for(CourseTypePersistance p:set){
            typees.add(new CourseType(p));
        }
        return Response.ok(toJson(typees), "application/json").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/{id: \\d+}")
    public Response getJson(
            @PathParam("id") int id
    ) {
        checkContext();
        return toRequestedType(id, "application/json");
    }

    @GET
    @Path("/plain")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain() {
        checkContext();
        Set<CourseTypePersistance> typees = dao.getAll();
        String msg = dao.count() + "\n";
        for (CourseTypePersistance b : typees) {
            msg = msg + b.getCourseTypeId()  + ";" + b.getCourseTypeName() +  "\n";
        }
        return msg;
    }

    @GET
    @Path("/plain/{id: \\d+}")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain(
            @PathParam("id") int id
    ) {
        checkContext();
        CourseTypePersistance type = dao.findByID(id);
        String msg = type.getCourseTypeId() + ";" + type.getCourseTypeName();
        return msg;
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/create")
    public Response createJson(
            @QueryParam("type_name") String type_name
    ) {
        checkContext();
        String msg = null;
        // Require both properties to create.
        if (type_name == null ) {
            msg = "Property 'type_name' is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        // Otherwise, create the Mail and add it to the database.
        CourseTypePersistance type = new CourseTypePersistance();
        type.setCourseTypeName(type_name);
        dao.save(type);
        return Response.ok(toJson(new CourseType(type)), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/create")
    public Response createPlain(
            @QueryParam("type_name") String type_name
    ) {
        checkContext();
        String msg = null;
        // Require both properties to create.
        if (type_name == null) {
            msg = "Property 'type_name' is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        // Otherwise, create the Mail and add it to the database.
        CourseTypePersistance type = new CourseTypePersistance();
        type.setCourseTypeName(type_name);
        dao.save(type);
        int id = type.getCourseTypeId();
        msg = id + ";" + type_name + "\n";
        return Response.ok(msg, "text/plain").build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("json/update/{id: \\d+}")
    public Response updateJson(
            @PathParam("id") int id,
            @QueryParam("type_name") String type_name
    ) {
        checkContext();

        System.out.println("in put json got request for id " + id);
        CourseTypePersistance type = dao.findByID(id);
        // Check that sufficient data are present to do an edit.
        String msg = null;
        if (type_name == null) {
            msg = "type_name is not given: nothing to edit\n";
        } else if (type == null) {
            msg = "There is no type with id " + id + "\n";
        }

        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            // Update.
            type.setCourseTypeName(type_name);
        }
        dao.update(type);

        return Response.ok(toJson(new CourseType(type)), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("plain/update/{id: \\d+}")
    public Response update(@FormParam("id") int id,
            @FormParam("type_name") String type_name) {
        checkContext();

        // Check that sufficient data are present to do an edit.
        String msg = null;
        if (type_name == null) {
            msg = "type_name is not given: nothing to edit\n";
        }

        CourseTypePersistance type = dao.findByID(id);
        if (type == null) {
            msg = "There is no type with id " + id + "\n";
        }

        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }else{
            type.setCourseTypeName(type_name);
        }
        dao.update(type);
        msg = id + ";" + type_name + "\n";
        return Response.ok(msg, "text/plain").build();
    }

    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        checkContext();
        String msg = null;
        CourseTypePersistance type = dao.findByID(id);
        if (type == null) {
            msg = "There is no type with id " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        dao.delete(type);
        msg = "Branch " + id + " deleted.\n";

        return Response.ok(msg, "text/plain").build();
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new CourseTypeDao();
        }
    }



    // CourseTypePersistance --> JSON document
    private String toJson(CourseType type) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(type);
        } catch (Exception e) {
        }
        return json;
    }

    // CourseTypePersistance set --> JSON document
    private String toJson(Set<CourseType> typeSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(typeSet);
        } catch (Exception e) {
        }
        return json;
    }

    // Generate an HTTP error response or typed OK response.
    private Response toRequestedType(int id, String type) {
        CourseTypePersistance ctype = dao.findByID(id);
        if (type == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else if (type.contains("json")) {
            return Response.ok(toJson(new CourseType(ctype)), type).build();
        } else {
            return Response.ok(new CourseType(ctype), type).build(); // toXml is automatic
        }
    }
}
