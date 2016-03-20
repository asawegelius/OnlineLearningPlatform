/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import se.wegelius.olpstudenthandler.model.CourseBranch;
import java.util.Set;
import org.hibernate.Query;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        Set<CourseBranch> testBranch = dao.getAll();
        for (CourseBranch b : testBranch) {
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
        CourseBranch cb = new CourseBranch();
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
        Set<CourseBranch> testBranch = dao.getAll();
        assertTrue(testBranch.size() == sum);
    }

    /**
     *
     */
    @Test
    public void testFindById() {
        CourseBranchDao dao = new CourseBranchDao();
        CourseBranch cb = new CourseBranch();
        cb.setCourseBranchName("New Branch");
        dao.saveOrUpdate(cb);
        Set<CourseBranch> testBranch = dao.getAll();
        for (CourseBranch b : testBranch) {
            if (b.getCourseBranchName().equals("New Branch")) {
                // find by id
                CourseBranch tb = dao.findByID(b.getCourseBranchId());
                assertTrue(tb.getCourseBranchName().equals("New Branch"));
            }
        }
    }

    /**
     *
     */
    @Test
    public void testGetAllAndDelete() {
        CourseBranchDao dao = new CourseBranchDao();
        Set<CourseBranch> testBranch = dao.getAll();
        for (CourseBranch b : testBranch) {
            dao.delete(b);
        }
        assertTrue(dao.count() == 0);
    }
}
