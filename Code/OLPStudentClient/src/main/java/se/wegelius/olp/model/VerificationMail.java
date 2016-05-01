/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.model;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author asawe
 */
public class VerificationMail {

    public static void sendEmailRegistrationLink(String email, String code) throws AddressException, MessagingException {
        String smtp = "send.one.com";
        String port = "465";
        final String from = "olp@wegelius.se";
        final String password = "olp-password";
        Properties props = new Properties();
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        //String link = "http://188.181.85.75/OLP/verify?go=" + code;
        String link = "http://localhost:8080/OLP/verify?go=" + code;

        StringBuilder bodyText = new StringBuilder();
        bodyText.append("<div>")
                .append("  Dear User<br/><br/>").append("  Thank you for registration. Your mail (")
                .append(email)
                .append(") is under verification<br/>")
                .append("  Please click <a href=\"")
                .append(link)
                .append("\">here</a> or open below link in browser<br/>")
                .append("  <a href=\"")
                .append(link)
                .append("\">")
                .append(link)
                .append("</a>")
                .append("  <br/><br/>")
                .append("  Thanks,<br/>")
                .append("  OLP Team")
                .append("</div>");
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(email));
        message.setSubject("Email Registration");
        message.setContent(bodyText.toString(), "text/html; charset=utf-8");
        Transport.send(message);
    }
}
