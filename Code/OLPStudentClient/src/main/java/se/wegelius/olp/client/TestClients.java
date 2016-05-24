/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.ClientResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.LoggerFactory;
import se.wegelius.olp.model.Course;
import se.wegelius.olp.model.CourseBranch;
import se.wegelius.olp.model.Encryptor;
import se.wegelius.olp.model.Lecture;
import se.wegelius.olp.model.LectureDeserializer;
import se.wegelius.olp.model.User;

/**
 *
 * @author asawe
 */
public class TestClients {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TestClients.class);

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException {
        //VerificationTokenClient tokenClient = new VerificationTokenClient();
        UserClient userClient = new UserClient();
        Encryptor encryptor = new Encryptor();
        String email = "asa@wegelius.se";
        String pw = "qwert";
        String encryptedPw = encryptor.get_SHA_512_SecurePassword(pw);
        MultivaluedMap map = userClient.getParameters(null, email, encryptedPw, 0);
        ClientResponse response = userClient.createJson(map);
        String userJson = response.getEntity(String.class);
        logger.info(userJson);
        if (response.getStatusInfo().getReasonPhrase().equals("OK")) {
            User user = new Gson().fromJson(userJson, User.class);
            logger.info(user.getUserName());
            String pw2 = user.getPassword();
            logger.info("pw: " + pw + " pw2 " + pw2 + " encryptedPw: " + encryptedPw + " pw2 equal to base64: " + pw2.equals(encryptedPw));
            String pw3 = "qwert";
            logger.info("encrypter compare the string qwert with the saved password and gets: " + encryptor.authenticate(pw3, pw2));
        } else if (userJson.startsWith("user inactive")) {
            int id = Integer.parseInt(userJson.substring(userJson.indexOf(";") + 1));
            response = userClient.getJson(id);
            userJson = response.getEntity(String.class);
            User user = new Gson().fromJson(userJson, User.class);
            logger.info(user.getUserName());
            String pw2 = user.getPassword();
            logger.info("pw: " + pw + " pw2 " + pw2 + " encryptedPw: " + encryptedPw + " pw2 equal to base64: " + pw2.equals(encryptedPw));
            String pw3 = "qwert";
            logger.info("encrypter compare the string qwert with the saved password and gets: " + encryptor.authenticate(pw3, pw2));
        }

        CourseBranchClient branchClient = new CourseBranchClient();
        String jsonBranches = branchClient.getJson().getEntity(String.class);
        List<CourseBranch> branches = new Gson().fromJson(jsonBranches, new TypeToken<List<CourseBranch>>() {
        }.getType());
        CourseClient courseClient = new CourseClient();
        String jsonCourses = courseClient.getJson().getEntity(String.class);
        logger.info(jsonCourses);
        List<Course> courses = new Gson().fromJson(jsonCourses, new TypeToken<List<Course>>() {
        }.getType());

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Lecture.class, new LectureDeserializer());

        Gson gson = builder.create();
        LectureClient lClient = new LectureClient();
        String jsonLectures = lClient.getJsonCourse(1).getEntity(String.class);
        logger.info(jsonLectures);
        String jLecture = lClient.getJson(1).getEntity(String.class);
        logger.info(jLecture);
        Lecture lecture = gson.fromJson(jLecture, Lecture.class);
        logger.info(lecture.getLectureName());
        Lecture[] lectureArray = (Lecture[]) gson.fromJson(jsonLectures, Lecture[].class);
        for (Lecture lect : lectureArray) {
            logger.info(lect.getLectureName());
        }
    }

}
