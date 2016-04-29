/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.client;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.Date;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author asawe
 */
public class VerificationTokenClient extends GenericClient {

    private static final String BASE_URI = "http://localhost:8080/OlpStudentHandler/rest/token/";
    //private static final String BASE_URI = "http://188.181.85.75/OlpStudentHandler/rest/token/";   

    public VerificationTokenClient() {
        super(BASE_URI);
    }

    public MultivaluedMap getParameters(int id, int user_id, String token, Date expiry_day) {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("id", id);
        queryParams.add("user_id", user_id);
        queryParams.add("token", token);
        queryParams.add("expiry_day", expiry_day);
        return queryParams;
    }

    public MultivaluedMap getParameters(int user_id, String token, Date expiry_day) {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("user_id", user_id);
        queryParams.add("token", token);
        queryParams.add("expiry_day", expiry_day);
        return queryParams;
    }

}
