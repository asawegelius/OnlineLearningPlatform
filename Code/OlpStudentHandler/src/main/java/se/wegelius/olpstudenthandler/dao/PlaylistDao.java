/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import se.wegelius.olpstudenthandler.model.persistance.PlaylistPersistance;

/**
 *
 * @author asawe
 */
public class PlaylistDao extends OlpDao<PlaylistPersistance, Integer>{
    
    public PlaylistDao(Class<PlaylistPersistance> type) {
        super(type);
    }
   
    public PlaylistDao(){
        super(PlaylistPersistance.class);
    }
}
