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
import se.wegelius.olpstudenthandler.dao.CourseBranchDao;
import se.wegelius.olpstudenthandler.model.CourseBranch;
import se.wegelius.olpstudenthandler.model.persistance.CourseBranchPersistance;
/**
 *
 * @author asawe
 */
@Path("/branch")
public class CourseBranchService {
    
    @Context
    private ServletContext sctx;          // dependency injection
    private static CourseBranchDao dao;

    public CourseBranchService() {
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
        Set<CourseBranchPersistance> set = dao.getAll();
        Set<CourseBranch> branches = new HashSet<>();
        for(CourseBranchPersistance p:set){
            branches.add(new CourseBranch(p));
        }
        return Response.ok(toJson(branches), "application/json").build();
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
        Set<CourseBranchPersistance> branches = dao.getAll();
        String msg = dao.count() + "\n";
        for (CourseBranchPersistance b : branches) {
            msg = msg + b.getCourseBranchId()  + ";" + b.getCourseBranchName() +  "\n";
        }
        return msg;
    }

    @GET
    @Path("/plain/{id: \\d+}")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain(@PathParam("id") int id) {
        checkContext();
        CourseBranchPersistance branch = dao.findByID(id);
        String msg = branch.getCourseBranchId() + ";" + branch.getCourseBranchName();
        return msg;
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/create")
    public Response createJson(@QueryParam("branch_name") String branch_name) {
        checkContext();
        String msg = null;
        // Require both properties to create.
        if (branch_name == null ) {
            msg = "Property 'branch_name' is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        // Otherwise, create the Mail and add it to the database.
        CourseBranchPersistance branch = new CourseBranchPersistance();
        branch.setCourseBranchName(branch_name);
        dao.save(branch);
        return Response.ok(toJson(new CourseBranch(branch)), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/create")
    public Response createPlain(@QueryParam("branch_name") String branch_name) {
        checkContext();
        String msg = null;
        // Require both properties to create.
        if (branch_name == null) {
            msg = "Property 'branch_name' is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        // Otherwise, create the Mail and add it to the database.
        CourseBranchPersistance branch = new CourseBranchPersistance();
        branch.setCourseBranchName(branch_name);
        dao.save(branch);
        int id = branch.getCourseBranchId();
        msg = id + ";" + branch_name + "\n";
        return Response.ok(msg, "text/plain").build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("json/update/{id: \\d+}")
    public Response updateJson(@PathParam("id") int id,
            @QueryParam("branch_name") String branch_name) {
        checkContext();

        System.out.println("in put json got request for id " + id);
        CourseBranchPersistance branch = dao.findByID(id);
        // Check that sufficient data are present to do an edit.
        String msg = null;
        if (branch_name == null) {
            msg = "branch_name is not given: nothing to edit\n";
        } else if (branch == null) {
            msg = "There is no branch with id " + id + "\n";
        }

        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            // Update.
            branch.setCourseBranchName(branch_name);
        }
        dao.update(branch);

        return Response.ok(toJson(new CourseBranch(branch)), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("plain/update/{id: \\d+}")
    public Response update(@FormParam("id") int id,
            @FormParam("branch_name") String branch_name) {
        checkContext();

        // Check that sufficient data are present to do an edit.
        String msg = null;
        if (branch_name == null) {
            msg = "branch_name is not given: nothing to edit\n";
        }

        CourseBranchPersistance branch = dao.findByID(id);
        if (branch == null) {
            msg = "There is no branch with id " + id + "\n";
        }

        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }else{
            branch.setCourseBranchName(branch_name);
        }
        dao.update(branch);
        msg = id + ";" + branch_name + "\n";
        return Response.ok(msg, "text/plain").build();
    }

    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        checkContext();
        String msg = null;
        CourseBranchPersistance branch = dao.findByID(id);
        if (branch == null) {
            msg = "There is no branch with id " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        dao.delete(branch);
        msg = "Branch " + id + " deleted.\n";

        return Response.ok(msg, "text/plain").build();
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new CourseBranchDao();
        }
    }



    // CourseBranchPersistance --> JSON document
    private String toJson(CourseBranch branch) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(branch);
        } catch (Exception e) {
        }
        return json;
    }

    // CourseBranchPersistance set --> JSON document
    private String toJson(Set<CourseBranch> branchSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(branchSet);
        } catch (Exception e) {
        }
        return json;
    }

    // Generate an HTTP error response or typed OK response.
    private Response toRequestedType(int id, String type) {
        CourseBranchPersistance branch = dao.findByID(id);
        if (branch == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else if (type.contains("json")) {
            return Response.ok(toJson(new CourseBranch(branch)), type).build();
        } else {
            return Response.ok(new CourseBranch(branch), type).build(); // toXml is automatic
        }
    }
}
