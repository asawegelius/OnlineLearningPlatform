/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import java.util.Set;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import se.wegelius.olpstudenthandler.model.persistance.CourseTypePersistance;

/**
 *
 * @author asawe
 */
public class CourseTypeDao  extends OlpDao<CourseTypePersistance, Integer> {
    
    CourseBranchDao CBD = new CourseBranchDao();
    
    public CourseTypeDao( Class<CourseTypePersistance> type) {
        super(type);
    }

    /**
     *
     */
    public CourseTypeDao() {
        super();
    }
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Set<CourseTypePersistance> GetTypeXML() {
        return super.getAll();
    }
    
    @GET
    @Produces({ MediaType.TEXT_XML })
    public Set<CourseTypePersistance> GetTypeHTML() {
    return super.getAll();
    }
    
    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public CourseTypePersistance GetTypeByIdXML(@PathParam("id") int id) {
        return super.findByID(id);
    }
    
    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.TEXT_XML })
    public CourseTypePersistance GetTypeByIdHTML
        (@PathParam("id") int id
    ) {
        return super.findByID(id);
    }
    
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.TEXT_HTML })
    @Transactional
    public Response createCourseType (
           @FormParam("id") int id,
           @FormParam("name") String name
    ) {
        CourseTypePersistance newCT = new CourseTypePersistance(id, name);
        super.save(newCT);
        	return Response
			.status(Response.Status.CREATED)// 201
			.entity("A new entity has been created at /courseType/"
					+ newCT.getCourseTypeId())
			.header("Location",
					"http://localhost/OlpStudentHandler/rest/courseType/"
							+ newCT.getCourseTypeId()).build();
    }
    
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.TEXT_HTML })
    @Transactional
    public Response deleteCourseType (
            @PathParam("id") int id
    ) {
        CourseTypePersistance CT = super.findByID(id);
        super.delete(CT);
        return Response
			.status(Response.Status.CREATED)// 201
			.entity("An entity as been deleted : id was "
					+ id )
			.header("Location",
					"http://localhost/OlpStudentHandler/rest/courseType/"
							+ id).build();
    }
    
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.TEXT_HTML })
    @Transactional
    public Response saveOrUpdateCourseType (
            @PathParam("id") int id,
            @PathParam("name") String name,
            @PathParam("branchFK") int branchFK
    ) {
        
        CourseTypePersistance CT = new CourseTypePersistance(id, name);
        CT.setCourseBranch(CBD.findByID(branchFK));
        super.saveOrUpdate(CT);
       
        return Response
                .status(Response.Status.CREATED)// 201
                .entity("A new entity has been saved or updated at /courseType/"
                                + id)
                .header("Location",
                                "http://localhost/OlpStudentHandler/rest/courseType/"
                                                + id).build();
        
    }
}
