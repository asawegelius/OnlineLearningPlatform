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
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author asawe
 */
public class GenericClient {

    private final WebResource webResource;
    private final Client client;

    public GenericClient(String base_uri) {

        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(base_uri);
    }

    public <T> T getXml(Class<T> responseType, String id) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("xml/{0}", new Object[]{id}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public ClientResponse createJson(MultivaluedMap queryParams) throws UniformInterfaceException {
        ClientResponse response = webResource.queryParams(queryParams).path("json/create").post(ClientResponse.class);
        return response;
    }

    public ClientResponse create(MultivaluedMap queryParams) throws UniformInterfaceException {
        ClientResponse response = webResource.queryParams(queryParams).path("plain/create").post(ClientResponse.class);
        return response;
    }

    public ClientResponse updateJson(MultivaluedMap queryParams, int id) throws UniformInterfaceException {
        return webResource.queryParams(queryParams).path(java.text.MessageFormat.format("json/update/{0}", new Object[]{id})).put(ClientResponse.class);
    }

    public ClientResponse updatePlain(MultivaluedMap queryParams, int id) throws UniformInterfaceException {
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
        /*GenericType<Course> gType = new GenericType<Course>() {
        };*/
        return getJson(ClientResponse.class, id);
    }

    public <T> T getJson(Class<T> responseType, int id) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("json/{0}", new Object[]{id}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.destroy();
    }

    public WebResource getWebResource() {
        return webResource;
    }

    public Client getClient() {
        return client;
    }

}
