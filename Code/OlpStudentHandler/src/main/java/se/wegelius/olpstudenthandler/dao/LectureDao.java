/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import se.wegelius.olpstudenthandler.model.persistance.LecturePersistance;

/**
 *
 * @author asawe
 */
public class LectureDao extends OlpDao<LecturePersistance, Integer>{
    public LectureDao(){
        super(LecturePersistance.class);
    }
}
