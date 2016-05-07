/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.LoggerFactory;
import se.wegelius.olpstudenthandler.model.persistance.UserPersistance;

/**
 *
 * @author asawe
 */
public class UserDaoTest {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserDaoTest.class);
        int user_id = -1;
        UserDao userDao;
    public UserDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        userDao = new UserDao();
    }

    @After
    public void tearDown() {
        userDao.delete(userDao.findByID(user_id));
        userDao = null;       
    }

    /**
     * Test of save method, of class OlpDao.
     */
    @Test
    public void testSave() {
        System.out.println("save");

        UserPersistance user = new UserPersistance();
        user.setUserName("test@gmail.com");
        user.setPassword("test");
        user.setEnabled(true);
        int sum = userDao.count();
        userDao.save(user);
        int newSum = userDao.count();
        logger.info("the new user: " + user.getPassword() + ", " + user.getUserName() + ", " + user.getUserId() + ", " + user.isEnabled());
        user_id = user.getUserId();
        logger.info("sum = " + sum + " newSum = " + newSum);
        assertTrue(sum < newSum);
    }

}
