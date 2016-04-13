/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import se.wegelius.olpstudenthandler.model.persistance.UserPersistance;

/**
 *
 * @author asawe
 */
public class UserDao  extends OlpDao<UserPersistance, Integer> {
    
    public UserDao(Class<UserPersistance> type) {
        super(type);
    }
    
    public UserDao(){
        super(UserPersistance.class);
    }
}
