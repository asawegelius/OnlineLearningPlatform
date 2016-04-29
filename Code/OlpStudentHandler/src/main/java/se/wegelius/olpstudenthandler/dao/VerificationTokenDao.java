/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import se.wegelius.olpstudenthandler.model.persistance.VerificationtokenPersistance;

/**
 *
 * @author asawe
 */
public class VerificationTokenDao extends OlpDao<VerificationtokenPersistance, Integer>{
    
      public VerificationTokenDao(Class<VerificationtokenPersistance> type) {
        super(type);
    }
    
    public VerificationTokenDao(){
        super(VerificationtokenPersistance.class);
    }  
}
