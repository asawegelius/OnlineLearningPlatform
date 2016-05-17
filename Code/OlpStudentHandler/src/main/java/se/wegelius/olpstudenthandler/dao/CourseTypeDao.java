/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import se.wegelius.olpstudenthandler.model.persistance.CourseTypePersistance;

/**
 *
 * @author asawe
 */
public class CourseTypeDao  extends OlpDao<CourseTypePersistance, Integer> {

    
    public CourseTypeDao( Class<CourseTypePersistance> type) {
        super(type);
    }

    /**
     *
     */
    public CourseTypeDao() {
        super(CourseTypePersistance.class);
    }

    
}
