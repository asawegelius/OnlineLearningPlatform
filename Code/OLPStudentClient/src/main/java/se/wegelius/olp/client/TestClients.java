/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.client;

import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.LoggerFactory;
import se.wegelius.olp.model.Encryptor;
import se.wegelius.olp.model.User;

/**
 *
 * @author asawe
 */
public class TestClients {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TestClients.class);

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
        //VerificationTokenClient tokenClient = new VerificationTokenClient();
        UserClient userClient = new UserClient();
        Encryptor encryptor = new Encryptor();
        String email = "asa@wegelius.se";
        String pw = "qwert";
        String encryptedPw = encryptor.get_SHA_512_SecurePassword(pw);
        MultivaluedMap map = userClient.getParameters(email, encryptedPw, 0);
        ClientResponse response = userClient.createJson(map);
        String userJson = response.getEntity(String.class);
        User user = new Gson().fromJson(userJson, User.class);
        logger.info(user.getUserName());
        String pw2 = user.getPassword();
        logger.info("pw: " + pw + " pw2 " + pw2 + " encryptedPw: " + encryptedPw + " pw2 equal to base64: " + pw2.equals(encryptedPw));
        String pw3 = "qwert";
        logger.info("encrypter compare the string qwert with the saved password and gets: " + encryptor.authenticate(pw3, pw2));
    }

}
