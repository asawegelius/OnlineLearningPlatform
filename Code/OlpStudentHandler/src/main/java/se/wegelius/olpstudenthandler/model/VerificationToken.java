/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import se.wegelius.olpstudenthandler.model.persistance.VerificationtokenPersistance;

/**
 *
 * @author asawe
 */
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;
    private int id;    
    private String token;
    private User user;    
    private Date expiryDate;
 
    public VerificationToken() {
        super();
    }
    
    public VerificationToken(VerificationtokenPersistance p){
        this.token = p.getToken();
        this.user = new User(p.getUser());
        this.expiryDate = p.getExpiryDate();
    }
    public VerificationToken(String token, User user) {
        super();
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
     
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }



    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}
