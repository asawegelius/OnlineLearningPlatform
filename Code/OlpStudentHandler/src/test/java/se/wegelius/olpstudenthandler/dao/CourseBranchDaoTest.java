/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import se.wegelius.olpstudenthandler.model.CourseBranch;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author asawe
 */
public class CourseBranchDaoTest {

    CourseBranchDao dao;

    public CourseBranchDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        dao = new CourseBranchDao();
    }

    @After
    public void tearDown() {
        if (dao != null) {
            if (dao.sessionFactory != null) {
                dao.sessionFactory.close();
            }
        }
    }

    /**
     *
     */
    @org.junit.Test
    public void testCourseBranch() {
       
        // create a test branch...
        int sum = dao.count();
        dao.save(new CourseBranch("Test Branch"));
        int newSum = dao.count();   
        System.out.println("newSum = " + newSum + " sum = " + sum);
        //assertTrue(sum == (newSum-1));
        // find by name
        CourseBranch testBranch = dao.findByName("Test Branch");
        assertTrue(testBranch.getBranchName().equals("Test Branch"));
        // find by id
        CourseBranch tb = dao.findByID(testBranch.getBranchId());
        assertTrue(tb.getBranchName().equals("Test Branch"));

        //delete the test branch
        dao.delete(testBranch);
        assertTrue(sum == dao.count());
    }
}
