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
import org.hibernate.Hibernate;
import se.wegelius.olpstudenthandler.dao.CourseDao;
import se.wegelius.olpstudenthandler.model.Course;
import se.wegelius.olpstudenthandler.model.persistance.CoursePersistance;

/**
 *
 * @author asawe
 */
@Path("/course")
public class CourseService {

    @Context
    private ServletContext sctx;          // dependency injection
    private static CourseDao dao;

    public CourseService() {
    }

    @GET
    @Path("/xml")
    @Produces({MediaType.APPLICATION_XML})
    public Response getXml() {
        checkContext();        
        Set<CoursePersistance> set = dao.getAll();
        Set<Course> courses = new HashSet<>();
        for(CoursePersistance p:set){
            courses.add(new Course(p));
        }
        return Response.ok(courses, "application/xml").build();
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
        Set<CoursePersistance> set = dao.getAll();
        Set<Course> courses = new HashSet<>();
        for(CoursePersistance p:set){
            courses.add(new Course(p));
        }
        return Response.ok(toJson(courses), "application/json").build();
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
        Set<CoursePersistance> courses = dao.getAll();
        String msg = dao.count() + "\n";
        for (CoursePersistance course : courses) {
            msg = msg +  course.getCourseId() + ";" + course.getCourseName() + ";" + course.getCourseDescription() + ";"
                    + course.getSkillLevel() + ";" + course.getLanguage() + ";"+ course.getCourseBranch().getCourseBranchId() + "\n";
        }
        return msg;
    }

    @GET
    @Path("/plain/{id: \\d+}")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain(@PathParam("id") int id) {
        checkContext();
        CoursePersistance course = dao.findByID(id);
        String msg = course.getCourseId() + ";" + course.getCourseName() + ";" + course.getCourseDescription() + ";"
                + course.getSkillLevel() + ";" + course.getLanguage() + "\n";
        return msg;
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/create")
    public Response createJson(@QueryParam("course_name") String course_name,
            @QueryParam("description") String description,
            @QueryParam("skill_level") String skill_level,
            @QueryParam("language") String language) {
        checkContext();
        // Require name to create.
        if (course_name == null) {
            String msg = "Property 'course_name' is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        // Otherwise, create the Mail and add it to the database.
        CoursePersistance coursePersistance = new CoursePersistance();
        coursePersistance.setCourseName(course_name);
        coursePersistance.setCourseDescription(description);
        coursePersistance.setLanguage(language);
        coursePersistance.setSkillLevel(skill_level);
        dao.save(coursePersistance);
        Course course = new Course(coursePersistance);
        return Response.ok(toJson(course), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/create")
    public Response createPlain(@QueryParam("course_name") String course_name,
            @QueryParam("description") String description,
            @QueryParam("skill_level") String skill_level,
            @QueryParam("language") String language) {
        checkContext();
        String msg = null;
        // Require name to create.
        if (course_name == null) {
            msg = "Property 'course_name' is missing.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        // Otherwise, create the Mail and add it to the database.
        CoursePersistance course = new CoursePersistance();
        course.setCourseName(course_name);
        course.setCourseDescription(description);
        course.setLanguage(language);
        course.setSkillLevel(skill_level);
        dao.save(course);
        int id = course.getCourseId();
        msg = id + ";" + course_name + "\n";
        return Response.ok(msg, "text/plain").build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("json/update/{id: \\d+}")
    public Response updateJson(@QueryParam("id") int id,
            @QueryParam("course_name") String course_name,
            @QueryParam("description") String description,
            @QueryParam("skill_level") String skill_level,
            @QueryParam("language") String language) {
        checkContext();

        System.out.println("in put json got request for id " + id);
        CoursePersistance course = dao.findByID(id);
        // Check that sufficient data are present to do an edit.
        String msg = null;
        if (course_name == null && description == null && skill_level == null && language == null) {
            msg = "No parameters is given: nothing to edit\n";
        } else if (course == null) {
            msg = "There is no course with id " + id + "\n";
        }

        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            // Update.
            course.setCourseName(course_name);
            course.setCourseDescription(description);
            course.setLanguage(language);
            course.setSkillLevel(skill_level);
        }
        dao.update(course);

        return Response.ok(toJson(new Course(course)), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("plain/update/{id: \\d+}")
    public Response update(@QueryParam("id") int id,
            @QueryParam("course_name") String course_name,
            @QueryParam("description") String description,
            @QueryParam("skill_level") String skill_level,
            @QueryParam("language") String language) {
        checkContext();

        // Check that sufficient data are present to do an edit.
        String msg = null;
        
        if (course_name == null && description == null && skill_level == null && language == null) {
            msg = "No parameters is given: nothing to edit\n";
        }

        CoursePersistance course = dao.findByID(id);
        if (course == null) {
            msg = "There is no course with id " + id + "\n";
        }

        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        } else {
            course.setCourseName(course_name);
            course.setCourseDescription(description);
            course.setLanguage(language);
            course.setSkillLevel(skill_level);
        }
        dao.update(course);
        msg = course.getCourseId() + ";" + course.getCourseName() + ";" + course.getCourseDescription() + ";"
                + course.getSkillLevel() + ";" + course.getLanguage() + "\n";
        return Response.ok(msg, "text/plain").build();
    }

    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        checkContext();
        String msg = null;
        CoursePersistance course = dao.findByID(id);
        if (course == null) {
            msg = "There is no course with id " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        dao.delete(course);
        msg = "Course " + id + " deleted.\n";

        return Response.ok(msg, "text/plain").build();
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new CourseDao();
        }
    }

    // CoursePersistance --> JSON document
    private String toJson(Course course) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(course);
        } catch (Exception e) {
        }
        return json;
    }

    // CoursePersistance set --> JSON document
    private String toJson(Set<Course> courseSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(courseSet);
        } catch (Exception e) {
        }
        return json;
    }

    // Generate an HTTP error response or typed OK response.
    private Response toRequestedType(int id, String type) {
        CoursePersistance course = dao.findByID(id);
        if (course == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else if (type.contains("json")) {
            return Response.ok(toJson(new Course(course)), type).build();
        } else {
            return Response.ok(new Course(course), type).build(); // toXml is automatic
        }
    }
}
