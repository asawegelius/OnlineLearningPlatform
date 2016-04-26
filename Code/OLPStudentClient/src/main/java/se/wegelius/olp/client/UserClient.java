/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author asawe
 */
public class UserClient extends GenericClient {

    private static final String BASE_URI = "http://localhost:8080/OlpStudentHandler/rest/user/";
    //private static final String BASE_URI = "http://188.181.85.75/OlpStudentHandler/rest/user/";

    public UserClient() {
        super(BASE_URI);
    }

    public MultivaluedMap getParameters(int id, String userName, String password, int enabled) {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("id", id);
        queryParams.add("user_name", userName);
        queryParams.add("enabled", enabled);
        return queryParams;
    }

    public MultivaluedMap getParameters(String userName, String password, int enabled) {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("user_name", userName);
        queryParams.add("enabled", enabled);
        return queryParams;
    }

}
