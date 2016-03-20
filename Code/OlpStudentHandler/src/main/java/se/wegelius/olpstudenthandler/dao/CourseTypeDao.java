/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import org.hibernate.SessionFactory;
import se.wegelius.olpstudenthandler.model.CourseType;

/**
 *
 * @author asawe
 */
public class CourseTypeDao  extends OlpDao<CourseType, Integer> {
    
    public CourseTypeDao(SessionFactory sessionFactory, Class<CourseType> type) {
        super(sessionFactory, type);
    }

    /**
     *
     */
    public CourseTypeDao() {
        super();
    }
}
