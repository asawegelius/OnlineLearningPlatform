/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import se.wegelius.olpstudenthandler.model.persistance.ContentProviderPersistance;

/**
 *
 * @author asawe
 */
public class ContentProviderDao  extends OlpDao<ContentProviderPersistance, Integer>  {
        
    public ContentProviderDao(Class<ContentProviderPersistance> type) {
        super(type);
    }

    /**
     *
     */
    public ContentProviderDao() {
        super(ContentProviderPersistance.class);
    }
}
