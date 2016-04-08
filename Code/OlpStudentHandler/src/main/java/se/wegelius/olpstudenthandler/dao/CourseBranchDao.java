/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import java.util.Set;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import se.wegelius.olpstudenthandler.model.CourseBranch;

/**
 *
 * @author asawe
 */

@Path("/courseBranch")
public class CourseBranchDao extends OlpDao<CourseBranch, Integer> {

    public CourseBranchDao(Class<CourseBranch> type) {
        super(type);
    }

    /**
     *
     */
    public CourseBranchDao() {
        super();
    }
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Set<CourseBranch> GetBranchXML() {
        return super.getAll();
    }
    
    @GET
    @Produces({ MediaType.TEXT_XML })
    public Set<CourseBranch> GetBranchHTML() {
        return super.getAll();
    }
    
    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public CourseBranch GetBranchByIdXML(@PathParam("id") int id) {
        return super.findByID(id);
    }
    
    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.TEXT_XML })
    public CourseBranch GetBranchByIdHTML
        (@PathParam("id") int id
    ) {
        return super.findByID(id);
    }
    
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.TEXT_HTML })
    @Transactional
    public Response createCourseBranch (
           @FormParam("name") String name
    ) {
        CourseBranch newCB = new CourseBranch(name);
        super.save(newCB);
        	return Response
			.status(Response.Status.CREATED)// 201
			.entity("A new entity has been created at /courseBranch/"
					+ newCB.getCourseBranchId())
			.header("Location",
					"http://localhost/OlpStudentHandler/rest/courseBranch/"
							+ newCB.getCourseBranchId().toString()).build();
    }
    
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.TEXT_HTML })
    @Transactional
    public Response deleteCourseBranch (
            @PathParam("id") int id
    ) {
        CourseBranch CB = super.findByID(id);
        super.delete(CB);
        return Response
			.status(Response.Status.CREATED)// 201
			.entity("An entity as been deleted : id was "
					+ id )
			.header("Location",
					"http://localhost/OlpStudentHandler/rest/courseBranch/"
							+ id).build();
    }
    
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.TEXT_HTML })
    @Transactional
    public Response saveOrUpdateCourseBranch (
            @PathParam("id") int id,
            @PathParam("name") String name
    ) {
         CourseBranch CB = new CourseBranch(name);
         CB.setCourseBranchId(id);
         
         super.saveOrUpdate(CB);
        
        return Response
                .status(Response.Status.CREATED)// 201
                .entity("A new entity has been saved or updated at /courseBranch/"
                                + id)
                .header("Location",
                                "http://localhost/OlpStudentHandler/rest/courseBranch/"
                                                + id).build();
        
    }
    
}
