/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import se.wegelius.olpstudenthandler.model.persistance.CoursePersistance;

/**
 *
 * @author asawe
 */
public class CourseDao  extends OlpDao<CoursePersistance, Integer> {
    
    public CourseDao(Class<CoursePersistance> type) {
        super(type);
    }

    /**
     *
     */
    public CourseDao() {
        super();
    }
}
