/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Time;
import java.util.HashMap;
import java.util.HashSet;
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
import se.wegelius.olpstudenthandler.dao.LectureDao;
import se.wegelius.olpstudenthandler.model.Lecture;
import se.wegelius.olpstudenthandler.model.persistance.CoursePersistance;
import se.wegelius.olpstudenthandler.model.persistance.LecturePersistance;

/**
 *
 * @author asawe
 */
@Path("/lecture")
public class LectureService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LectureService.class);
    @Context
    private ServletContext sctx;          // dependency injection
    private static LectureDao dao;

    public LectureService() {
    }

    @GET
    @Path("/xml")
    @Produces({MediaType.APPLICATION_XML})
    public Response getXml() {
        checkContext();
        Set<LecturePersistance> set = dao.getAll();
        Set<Lecture> lectures = new HashSet<>();
        for (LecturePersistance p : set) {
            lectures.add(new Lecture(p));
        }
        return Response.ok(lectures, "application/xml").build();
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
        Set<LecturePersistance> set = dao.getAll();
        Set<Lecture> lectures = new HashSet<>();
        for (LecturePersistance p : set) {
            lectures.add(new Lecture(p));
        }
        return Response.ok(toJson(lectures), "application/json").build();
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
        Set<LecturePersistance> lectures = dao.getAll();
        String msg = dao.count() + "\n";
        for (LecturePersistance lecture : lectures) {
            msg = msg + lecture.getLectureId() + ";" + lecture.getLectureName() + ";" + lecture.getVideo() + ";"
                    + lecture.getCourse().getCourseId() + ";" + lecture.getDuration() + "\n";
        }
        return msg;
    }

    @GET
    @Path("/plain/{id: \\d+}")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPlain(@PathParam("id") int id) {
        checkContext();
        LecturePersistance lecture = dao.findByID(id);
        String msg = lecture.getLectureId() + ";" + lecture.getLectureName() + ";" + lecture.getVideo() + ";"
                + lecture.getCourse().getCourseId() + ";" + lecture.getDuration() + "\n";
        return msg;
    }

    @GET
    @Path("/json/course/{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getJsonCourse(@PathParam("id") int id) {
        checkContext();
        Map params = new HashMap();
        params.put("course_id", id);
        Set<LecturePersistance> set = dao.query("SELECT l FROM LecturePersistance AS l WHERE l.course.courseId = :course_id", params);
        logger.info("lectures found: " + set.size() + " for course id " + id);
        Set<Lecture> lectures = new HashSet<>();
        for (LecturePersistance p : set) {
            lectures.add(new Lecture(p));
        }
        return Response.ok(toJson(lectures), "application/json").build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/create")
    public Response createJson(@QueryParam("lecture_name") String lecture_name,
            @QueryParam("video") String video,
            @QueryParam("course_id") int course_id,
            @QueryParam("duration") long duration,
            @QueryParam("description") String description) {
        checkContext();
        Time dur = null;
        // Require all properties to create.
        String msg = null;
        if (lecture_name == null) {
            msg = "lecture_id missing";
        }
        if (video == null) {
            if (msg == null) {
                msg = "video missing";
            } else {
                msg = msg + ";video missing";
            }
        }
        if (duration <= 0) {
            if (msg == null) {
                msg = "duration corrupted";
            } else {
                msg = msg + ";duration corrupted";
            }
        } else {
            dur = new Time(duration);
        }
        if (description == null) {
            if (msg == null) {
                msg = "description missing";
            } else {
                msg = msg + ";description missing";
            }
        }
        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        // get the lectures lecture
        CoursePersistance course = new CourseDao().findByID(course_id);
        if (course == null) {
            msg = msg + "course missing";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        LecturePersistance p = new LecturePersistance();
        p.setCourse(course);
        p.setDuration(dur);
        p.setLectureName(lecture_name);
        p.setVideo(video);
        p.setDescription(description);
        dao.save(p);

        return Response.ok(toJson(new Lecture(p)), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/create")
    public Response createPlain(@QueryParam("lecture_name") String lecture_name,
            @QueryParam("video") String video,
            @QueryParam("course_id") int course_id,
            @QueryParam("duration") long duration,
            @QueryParam("description") String description) {
        checkContext();
        Time dur = null;
        // Require all properties to create.
        String msg = null;
        if (lecture_name == null) {
            msg = "lecture_id missing";
        }
        if (video == null) {
            if (msg == null) {
                msg = "video missing";
            } else {
                msg = msg + ";video missing";
            }
        }
        if (duration <= 0) {
            if (msg == null) {
                msg = "duration corrupted";
            } else {
                msg = msg + ";duration corrupted";
            }
        } else {
            dur = new Time(duration);
        }
        if (description == null) {
            if (msg == null) {
                msg = "description missing";
            } else {
                msg = msg + ";description missing";
            }
        }
        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        // get the lectures lecture
        CoursePersistance course = new CourseDao().findByID(course_id);
        if (course == null) {
            msg = msg + "course missing";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        LecturePersistance p = new LecturePersistance();
        p.setCourse(course);
        p.setDuration(dur);
        p.setLectureName(lecture_name);
        p.setVideo(video);
        p.setDescription(description);
        dao.save(p);

        int id = p.getLectureId();
        msg = id + ";" + p.getLectureName() + "\n";
        return Response.ok(msg, "text/plain").build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("json/update/{id: \\d+}")
    public Response updateJson(@QueryParam("id") int id,
            @QueryParam("lecture_name") String lecture_name,
            @QueryParam("video") String video,
            @QueryParam("course_id") int course_id,
            @QueryParam("duration") long duration,
            @QueryParam("description") String description) {
        checkContext();
        Time dur = null;
        // Require all properties to create.
        String msg = null;
        if (lecture_name == null) {
            msg = "lecture_id missing";
        }
        if (video == null) {
            if (msg == null) {
                msg = "video missing";
            } else {
                msg = msg + ";video missing";
            }
        }
        if (duration <= 0) {
            if (msg == null) {
                msg = "duration corrupted";
            } else {
                msg = msg + ";duration corrupted";
            }
        } else {
            dur = new Time(duration);
        }
        if (description == null) {
            if (msg == null) {
                msg = "description missing";
            } else {
                msg = msg + ";description missing";
            }
        }
        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        // get the lectures lecture
        CoursePersistance course = new CourseDao().findByID(course_id);
        if (course == null) {
            msg = msg + "course missing";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        LecturePersistance lecture = dao.findByID(id);
        if (lecture == null) {
            msg = msg + "lecture missing";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        // Update.
        lecture.setCourse(course);
        lecture.setLectureName(lecture_name);
        lecture.setVideo(video);
        lecture.setDuration(dur);
        lecture.setDescription(description);
        dao.update(lecture);

        return Response.ok(toJson(new Lecture(lecture)), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("plain/update/{id: \\d+}")
    public Response update(@QueryParam("id") int id,
            @QueryParam("lecture_name") String lecture_name,
            @QueryParam("video") String video,
            @QueryParam("course_id") int course_id,
            @QueryParam("duration") long duration,
            @QueryParam("description") String description) {
        checkContext();
        Time dur = null;
        // Require all properties to create.
        String msg = null;
        if (lecture_name == null) {
            msg = "lecture_id missing";
        }
        if (video == null) {
            if (msg == null) {
                msg = "video missing";
            } else {
                msg = msg + ";video missing";
            }
        }
        if (duration <= 0) {
            if (msg == null) {
                msg = "duration corrupted";
            } else {
                msg = msg + ";duration corrupted";
            }
        } else {
            dur = new Time(duration);
        }
        if (description == null) {
            if (msg == null) {
                msg = "description missing";
            } else {
                msg = msg + ";description missing";
            }
        }
        if (msg != null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        // get the lectures lecture
        CoursePersistance course = new CourseDao().findByID(course_id);
        if (course == null) {
            msg = msg + "course missing";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        LecturePersistance lecture = dao.findByID(id);
        if (lecture == null) {
            msg = msg + "lecture missing";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        // Update.
        lecture.setCourse(course);
        lecture.setLectureName(lecture_name);
        lecture.setVideo(video);
        lecture.setDuration(dur);
        lecture.setDescription(description);
        dao.update(lecture);

        msg = lecture.getLectureId() + ";" + lecture.getLectureName() + "\n";
        return Response.ok(msg, "text/plain").build();
    }

    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        checkContext();
        String msg = null;
        LecturePersistance lecture = dao.findByID(id);
        if (lecture == null) {
            msg = "There is no course with id " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        dao.delete(lecture);
        msg = "Lecture " + id + " deleted.\n";

        return Response.ok(msg, "text/plain").build();
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new LectureDao();
        }
    }

    // LecturePersistance --> JSON document
    private String toJson(Lecture lecture) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(lecture);
        } catch (Exception e) {
        }
        return json;
    }

    // LecturePersistance set --> JSON document
    private String toJson(Set<Lecture> lectureSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(lectureSet);
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
        }
        return json;
    }

    // Generate an HTTP error response or typed OK response.
    private Response toRequestedType(int id, String type) {
        LecturePersistance lecture = dao.findByID(id);
        if (lecture == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else if (type.contains("json")) {
            return Response.ok(toJson(new Lecture(lecture)), type).build();
        } else {
            return Response.ok(new Lecture(lecture), type).build(); // toXml is automatic
        }
    }
}
