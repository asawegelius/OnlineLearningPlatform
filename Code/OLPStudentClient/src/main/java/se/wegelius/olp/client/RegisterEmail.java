/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.client;

import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.LoggerFactory;
import se.wegelius.olp.model.PasswordEncryptionService;
import se.wegelius.olp.model.User;
import se.wegelius.olp.model.VerificationMail;
import se.wegelius.olp.model.VerificationToken;

/**
 *
 * @author asawe
 */
@WebServlet("/RegisterEmail")
public class RegisterEmail extends HttpServlet {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RegisterEmail.class);

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterEmail() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // collect all input values
        String email = request.getParameter("remail");
        logger.info(email);
        String password = request.getParameter("rpassword");
        logger.info(password);
        User user = new User();
        user.setUserName(email);

        // generate hash for password
        PasswordEncryptionService encryptor = new PasswordEncryptionService();
        byte[] encryptedPassword = null;
        try {
            encryptedPassword = encryptor.getEncryptedPassword(password, encryptor.generateSalt());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(RegisterEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        String encryptedToString = new String(encryptedPassword, "ISO-8859-1");
        user.setPassword(encryptedToString);
        // generate unique code for email verification
        UUID uniqueid = UUID.randomUUID();
        String stringToken = uniqueid.toString();

        // verification token
        VerificationToken token = new VerificationToken();
        token.setToken(stringToken);
        token.setUser(user);

 
        // save the user as a not enabled user
        UserClient userClient = new UserClient();
        MultivaluedMap parameters = userClient.getParameters(user.getUserName(), user.getPassword(), 0);
        ClientResponse clientResponse = userClient.createJson(parameters);
        String msg = null;
        logger.info(clientResponse.getStatusInfo().getReasonPhrase());
        if(clientResponse.getStatusInfo().getReasonPhrase().equals("Bad Request")){
            msg = clientResponse.getEntity(String.class);
            logger.info("error message after trying to create user: " + msg);
        }
        if (msg == null) {
            // create account and verificationtoken if email not exists
            //TODO
            // send verification email
            try {
                VerificationMail.sendEmailRegistrationLink(email, stringToken);
            } catch (AddressException e) {
                logger.error(e.getMessage());
            } catch (MessagingException e) {
                logger.error(e.getMessage());
            }
            msg = "Registation Link Was Sent To Your Mail Successfully. Please Verify Your Email";
        } 
        else {
            
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            PrintWriter out= response.getWriter();
            out.println("<font color=red> " + msg +"</font>");
            rd.include(request, response);
        }

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        doPost(req, res);
    }
}
