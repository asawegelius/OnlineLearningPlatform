/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import se.wegelius.olpstudenthandler.model.persistance.CourseBranchPersistance;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author asawe
 */
public class CourseBranchDaoTest {

    public CourseBranchDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {      
        CourseBranchDao dao = new CourseBranchDao();
        Set<CourseBranchPersistance> testBranch = dao.getAll();
        for (CourseBranchPersistance b : testBranch) {
            if(b.getCourseBranchName().equals("Test Branch"))
                dao.delete(b);
             if(b.getCourseBranchName().equals("New Branch"))
                dao.delete(b);          
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     *
     */
    @Test
    public void testAddCourseBranch() {
        CourseBranchDao dao = new CourseBranchDao();
        // create a test branch...
        int sum = dao.count();
        CourseBranchPersistance cb = new CourseBranchPersistance();
        cb.setCourseBranchName("Test Branch");
        dao.saveOrUpdate(cb);
        int newSum = dao.count();
        System.out.println("newSum = " + newSum + " sum = " + sum);
        assertTrue(sum == (newSum - 1));
    }

    /**
     *
     */
    @Test
    public void testGetAll() {
        CourseBranchDao dao = new CourseBranchDao();
        int sum = dao.count();
        Set<CourseBranchPersistance> testBranch = dao.getAll();
        assertTrue(testBranch.size() == sum);
    }

    /**
     *
     */
    @Test
    public void testFindById() {
        CourseBranchDao dao = new CourseBranchDao();
        CourseBranchPersistance cb = new CourseBranchPersistance();
        cb.setCourseBranchName("New Branch");
        dao.saveOrUpdate(cb);
        Set<CourseBranchPersistance> testBranch = dao.getAll();
        for (CourseBranchPersistance b : testBranch) {
            if (b.getCourseBranchName().equals("New Branch")) {
                // find by id
                CourseBranchPersistance tb = dao.findByID(b.getCourseBranchId());
                assertTrue(tb.getCourseBranchName().equals("New Branch"));
            }
        }
    }


}
