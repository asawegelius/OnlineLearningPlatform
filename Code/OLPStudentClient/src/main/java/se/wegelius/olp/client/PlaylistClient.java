/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.client;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.LoggerFactory;

/**
 *
 * @author asawe
 */
public class PlaylistClient extends GenericClient {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PlaylistClient.class);
    private static final String BASE_URI = "http://localhost:8080/OlpStudentHandler/rest/playlist/";
    //private static final String BASE_URI = "http://188.181.85.75/OlpStudentHandler/rest/playlist/";

    public PlaylistClient() {
        super(BASE_URI);
    }

    public MultivaluedMap getParameters(int courseId, int userId) {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("course_id", Integer.toString(courseId));
        queryParams.add("user_id", Integer.toString(userId));
        return queryParams;
    }

    public MultivaluedMap getParameters(int id, int courseId, int userId) {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("id", Integer.toString(id));
        queryParams.add("course_id", Integer.toString(courseId));
        queryParams.add("user_id", Integer.toString(userId));
        return queryParams;
    }
    public ClientResponse getJsonByUser(int id) {
        WebResource resource = super.getWebResource();
        resource = resource.path("json/user/"+id);
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(ClientResponse.class);
    }
}
