/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.model;

import se.wegelius.olpstudenthandler.model.persistance.UserPersistance;

/**
 *
 * @author asawe
 */
public class User {
    

     private Integer userId;
     private String userName;
     private String password;

    public User() {
    }

    public User(String userName, String password) {
       this.userName = userName;
       this.password = password;
    }

    public User(UserPersistance p) {
        this.userName = p.getUserName();
        this.userId = p.getUserId();
        this.password = p.getPassword();
    }
   
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
