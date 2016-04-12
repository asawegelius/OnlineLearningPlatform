/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.ws.rs.core.MultivaluedMap;
import se.wegelius.olp.model.Course;

/**
 *
 * @author asawe
 */
public class CourseClient {

    private final WebResource webResource;
    private final Client client;
    private static final String BASE_URI = "http://localhost:8080/OlpStudentHandler/rest/course/";
    //private static final String BASE_URI = "http://188.181.85.75/OlpStudentHandler/rest/course/";

    public CourseClient() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI);
    }

    public <T> T getXml(Class<T> responseType, String id) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("xml/{0}", new Object[]{id}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public ClientResponse createJson(String email, String message) throws UniformInterfaceException {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("email", email);
        queryParams.add("message", message);
        System.out.println(queryParams.toString());
        ClientResponse response = webResource.queryParams(queryParams).path("json/create").post(ClientResponse.class);
        return response;
    }

    public ClientResponse create(String email, String message) throws UniformInterfaceException {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("email", email);
        queryParams.add("message", message);
        ClientResponse response = webResource.queryParams(queryParams).path("plain/create").post(ClientResponse.class);
        return response;
    }

    public ClientResponse updateJson(int id, String email, String message) throws UniformInterfaceException {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("id", Integer.toString(id));
        queryParams.add("email", email);
        queryParams.add("message", message);
        System.out.println(queryParams.toString());
        return webResource.queryParams(queryParams).path(java.text.MessageFormat.format("json/update/{0}", new Object[]{id})).put(ClientResponse.class);
    }

    public ClientResponse updatePlain(int id, String email, String message) throws UniformInterfaceException {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("id", Integer.toString(id));
        queryParams.add("email", email);
        queryParams.add("message", message);
        return webResource.queryParams(queryParams).path(java.text.MessageFormat.format("plain/update/{0}", new Object[]{id})).put(ClientResponse.class);
    }

    public ClientResponse delete(String id) throws UniformInterfaceException {
        return webResource.path(java.text.MessageFormat.format("plain/delete/{0}", new Object[]{id})).delete(ClientResponse.class);
    }

    public String getPlain() throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path("plain");
        return resource.accept(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public ClientResponse getJson() {
        WebResource resource = webResource;
        resource = resource.path("json");
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(ClientResponse.class);
    }

    public ClientResponse getJson(int id) {
        GenericType<Course> gType = new GenericType<Course>() {
        };
        return getJson(ClientResponse.class, Integer.toString(1));
    }

    public <T> T getJson(Class<T> responseType, String id) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("json/{0}", new Object[]{id}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.destroy();
    }

}
