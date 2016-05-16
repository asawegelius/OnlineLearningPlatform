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
public class CourseBranchClient extends GenericClient{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CourseBranchClient.class);
    private static final String BASE_URI = "http://localhost:8080/OlpStudentHandler/rest/branch/";
    //private static final String BASE_URI = "http://188.181.85.75/OlpStudentHandler/rest/branch/";
    
    public CourseBranchClient(){
        super(BASE_URI);
    }
    
       
    public MultivaluedMap getParameters(String courseBranchName){       
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("courseBranchName", courseBranchName);
        return queryParams;
    }      
    public MultivaluedMap getParameters(int id, String courseBranchName){       
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("id", id);
        queryParams.add("courseBranchName", courseBranchName);
        return queryParams;
    }
}

