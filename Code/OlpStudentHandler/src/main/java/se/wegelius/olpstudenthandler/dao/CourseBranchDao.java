/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import se.wegelius.olpstudenthandler.model.persistance.CourseBranchPersistance;

/**
 *
 * @author asawe
 */

public class CourseBranchDao extends OlpDao<CourseBranchPersistance, Integer> {

    public CourseBranchDao(Class<CourseBranchPersistance> type) {
        super(type);
    }

    /**
     *
     */
    public CourseBranchDao() {
        super(CourseBranchPersistance.class);
    }
    
}
