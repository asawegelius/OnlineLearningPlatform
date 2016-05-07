/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.LoggerFactory;
import se.wegelius.olp.model.Encryptor;
import se.wegelius.olp.model.User;
import se.wegelius.olp.model.VerificationMail;
import se.wegelius.olp.model.VerificationToken;

/**
 *
 * @author asawe
 */
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
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // collect all input values
        String email = request.getParameter("remail");
        logger.info(email);
        String password = request.getParameter("rpassword");
        logger.info(password);
        //User user = new User();
        //user.setUserName(email);

        // generate encrypted password
        String encryptedPassword = Encryptor.get_SHA_512_SecurePassword(password);

        UserClient userClient = new UserClient();
        // save the user as a not enabled user
        MultivaluedMap parameters = userClient.getParameters(email, encryptedPassword, 0);
        ClientResponse userResponse = userClient.createJson(parameters);
        String msg;
        User user = null;
        // check if response is a bad request
        if (userResponse.getStatusInfo().getReasonPhrase().equals("Bad Request")) {
            msg = userResponse.getEntity(String.class);
            logger.error("error message after trying to create user: " + msg);
            String reason = msg.substring(0, msg.indexOf(";"));
            logger.info("reason: " + reason);
            // user exists but is inactive so it is ok to procede
            if (msg.substring(0, msg.indexOf(";")).equals("user_name inactive")) {
                int user_id = -1;
                try {
                    user_id = Integer.parseInt(msg.substring(msg.indexOf(";") + 1).trim());
                    logger.info("user_id: " + user_id);
                } catch (NumberFormatException e) {
                    logger.error(e.getMessage());
                }
                if (user_id > -1) {
                    ClientResponse r = userClient.getJson(user_id);
                    String jsonUser = r.getEntity(String.class);
                    logger.info("jsonUser: " + jsonUser);
                    user = new Gson().fromJson(jsonUser, User.class);
                } else {
                    logger.error("corrupt user id");
                }
            }
            if (reason.equals("user_name active")) {

                msg = "The user " + email + " is already activated.";
                HttpSession session = request.getSession();
                session.setAttribute("msg", msg);
                String referer = request.getHeader("Referer");
                String encodedURL = response.encodeRedirectURL(referer);
                response.sendRedirect(encodedURL);
            }
        }
        if (userResponse.getStatusInfo().getReasonPhrase().equals("OK")) {
            String jsonUser = userResponse.getEntity(String.class);
            user = new Gson().fromJson(jsonUser, User.class);
            logger.info("the new user: " + user.getPassword() + ", " + user.getUserName()  + ", "+ user.getUserId());
        }
        if (user == null) {
            msg = "There was an error during login/register.";
            HttpSession session = request.getSession();
            session.setAttribute("msg", msg);
            String referer = request.getHeader("Referer");
            if (referer == null) {
                referer = "index.jsp";
            }
            String encodedURL = response.encodeRedirectURL(referer);
            response.sendRedirect(encodedURL);
        } else {
            // create the token
            UUID uniqueid = UUID.randomUUID();
            String stringToken = uniqueid.toString();
            logger.info("uniqueid: " + uniqueid + " stringToken: " + stringToken);

            logger.info("uniuserid: " + user.getUserId() + " stringToken: " + stringToken);
            // verification token
            VerificationToken token = new VerificationToken();
            token.setToken(stringToken);
            token.setUser(user);
            // create account and verificationtoken if email not exists
            VerificationTokenClient tokenClient = new VerificationTokenClient();
            MultivaluedMap tokenParam = tokenClient.getParameters(Integer.toString(user.getUserId()), token.getToken());
            ClientResponse tokenResponse = tokenClient.createJson(tokenParam);
            if (tokenResponse.getStatusInfo().getReasonPhrase().equals("Bad Request")) {
                msg = tokenResponse.getEntity(String.class);
                logger.error("error message after trying to create token: " + msg);
                if (msg.equals("user missing")) {
                    HttpSession session = request.getSession();
                    session.setAttribute("msg", "No user with " + email + ".");
                    String referer = request.getHeader("Referer");
                    if (referer == null) {
                        referer = "index.jsp";
                    }
                    String encodedURL = response.encodeRedirectURL(referer);
                    response.sendRedirect(encodedURL);
                }
            }
            if (tokenResponse.getStatusInfo().getReasonPhrase().equals("OK")) {
                // send verification email
                String jsonToken = tokenResponse.getEntity(String.class);
                GsonBuilder builder = new GsonBuilder();

                // Register an adapter to manage the date types as long values 
                builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    @Override
                    public Date deserialize(JsonElement je, java.lang.reflect.Type type, JsonDeserializationContext jdc) throws JsonParseException {
                        return new Date(je.getAsJsonPrimitive().getAsLong());
                    }
                });

                Gson gson = builder.create();
                token = gson.fromJson(jsonToken, VerificationToken.class);
                try {
                    VerificationMail.sendEmailRegistrationLink(email, token.getToken());
                } catch (AddressException e) {
                    logger.error(e.getMessage());
                } catch (MessagingException e) {
                    logger.error(e.getMessage());
                }
                logger.info(jsonToken);
                msg = "Registation Link Was Sent To Your Mail Successfully. Please Verify Your Email";
                HttpSession session = request.getSession();
                session.setAttribute("msg", msg);
                String encodedURL = response.encodeRedirectURL("index.jsp");
                response.sendRedirect(encodedURL);
            }
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        doPost(req, res);
    }
}
