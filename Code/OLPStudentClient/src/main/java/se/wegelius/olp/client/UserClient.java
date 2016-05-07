/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.client;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.LoggerFactory;

/**
 *
 * @author asawe
 */
public class UserClient extends GenericClient {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserClient.class);
    private static final String BASE_URI = "http://localhost:8080/OlpStudentHandler/rest/user/";
    //private static final String BASE_URI = "http://188.181.85.75/OlpStudentHandler/rest/user/";

    public UserClient() {
        super(BASE_URI);
    }

    public MultivaluedMap getParameters(int id, String userName, String password, int enabled) {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("id", Integer.toString(id));
        queryParams.add("user_name", userName);
        /*try {
            password = URLEncoder.encode(password, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }*/
        queryParams.add("password", password);
        queryParams.add("enabled", Integer.toString(enabled));
        return queryParams;
    }

    public MultivaluedMap getParameters(String userName, String password, int enabled) {
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("user_name", userName);
        /*try {
            password = URLEncoder.encode(password, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }*/
        queryParams.add("password", password);
        queryParams.add("enabled", Integer.toString(enabled));
        return queryParams;
    }

}
