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

   
    public ClientResponse getJsonToken(String token) {

        return getJsonToken(ClientResponse.class, token);
    }
    
   
    
    public ClientResponse getJsonToken(Class<ClientResponse> responseType, String token) {
        WebResource resource = super.getWebResource();
        resource = resource.path(java.text.MessageFormat.format("jsontoken/{0}", new Object[]{token}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(ClientResponse.class);

    }

    public MultivaluedMap getParameters(String id, String user_id, String token) {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("id", id);
        queryParams.add("user_id", user_id);
        queryParams.add("token", token);
        return queryParams;
    }

    public MultivaluedMap getParameters(String user_id, String token) {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("user_id", user_id);
        queryParams.add("token", token);
        return queryParams;
    }

}
