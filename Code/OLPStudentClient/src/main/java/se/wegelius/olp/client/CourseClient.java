/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.client;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.LoggerFactory;

/**
 *
 * @author asawe
 */
public class CourseClient extends GenericClient {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CourseClient.class);
    //private static final String BASE_URI = "http://localhost:8080/OlpStudentHandler/rest/course/";
    private static final String BASE_URI = "http://188.181.85.75/OlpStudentHandler/rest/course/";

    public CourseClient() {
        super(BASE_URI);
    }

    public MultivaluedMap getParameters(int id, String email, String message) {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("id", Integer.toString(id));
        queryParams.add("email", email);
        queryParams.add("message", message);
        return queryParams;
    }

    public MultivaluedMap getParameters(String email, String message) {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("email", email);
        queryParams.add("message", message);
        return queryParams;
    }
}
