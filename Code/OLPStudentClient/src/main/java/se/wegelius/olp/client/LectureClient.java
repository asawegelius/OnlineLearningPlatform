/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.client;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.sql.Time;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.LoggerFactory;

/**
 *
 * @author asawe
 */
public class LectureClient extends GenericClient {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LectureClient.class);
    //private static final String BASE_URI = "http://localhost:8080/OlpStudentHandler/rest/lecture/";
    private static final String BASE_URI = "http://188.181.85.75/OlpStudentHandler/rest/lecture/";

    public LectureClient() {
        super(BASE_URI);
    }

    public ClientResponse getJsonCourse(int courseId) {

        return getJsonCourse(ClientResponse.class, courseId);
    }

    public ClientResponse getJsonCourse(Class<ClientResponse> responseType, int courseId) {
        WebResource resource = super.getWebResource();
        resource = resource.path(java.text.MessageFormat.format("json/course/{0}", new Object[]{courseId}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(ClientResponse.class);

    }

    public MultivaluedMap getParameters(int id, int courseId, String lectureName, String video, Time duration, String description) {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("id", Integer.toString(id));
        queryParams.add("courseId", Integer.toString(courseId));
        queryParams.add("lectureName", lectureName);
        queryParams.add("video", video);
        queryParams.add("duration", duration);
        queryParams.add("description", description);
        return queryParams;
    }

    public MultivaluedMap getParameters(int courseId, String lectureName, String video, Time duration, String description) {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("courseId", Integer.toString(courseId));
        queryParams.add("lectureName", lectureName);
        queryParams.add("video", video);
        queryParams.add("duration", duration);
        queryParams.add("description", description);
        return queryParams;
    }
}
