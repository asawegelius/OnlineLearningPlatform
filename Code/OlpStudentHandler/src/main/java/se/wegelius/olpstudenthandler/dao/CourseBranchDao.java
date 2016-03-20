/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import se.wegelius.olpstudenthandler.model.CourseBranch;
import org.hibernate.SessionFactory;

/**
 *
 * @author asawe
 */
public class CourseBranchDao extends OlpDao<CourseBranch, Integer> {

    public CourseBranchDao(SessionFactory sessionFactory, Class<CourseBranch> type) {
        super(sessionFactory, type);
    }

    /**
     *
     */
    public CourseBranchDao() {
        super();
    }
}
