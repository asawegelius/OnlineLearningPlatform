/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author asawe
 */
public class PasswordEncryptionServiceTest {
    
    public PasswordEncryptionServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of authenticate method, of class PasswordEncryptionService.
     */
    @Test
    public void testAuthenticate() throws Exception {
        System.out.println("authenticate");
        PasswordEncryptionService instance = new PasswordEncryptionService();
        String attemptedPassword = "testAuthenticate";
        byte[] salt = instance.generateSalt();
        byte[] encryptedPassword = instance.getEncryptedPassword(attemptedPassword, salt);
        boolean expResult = true;
        boolean result = instance.authenticate(attemptedPassword, encryptedPassword, salt);
        assertEquals(expResult, result);
    }
    
       /**
     * Test of authenticate method, of class PasswordEncryptionService.
     */
    @Test
    public void testStringConvertion() throws Exception {
        System.out.println("string_convertion");
        PasswordEncryptionService instance = new PasswordEncryptionService();
        String attemptedPassword = "testStringConvertion";
        byte[] salt = instance.generateSalt();
        byte[] encryptedPassword = instance.getEncryptedPassword(attemptedPassword, salt);
        String encryptedToString = new String(encryptedPassword, "ISO-8859-1");
        byte[] backToByte = encryptedToString.getBytes("ISO-8859-1");
        assertArrayEquals(encryptedPassword, backToByte);
    }

    /**
     * Test of getEncryptedPassword method, of class PasswordEncryptionService.
     *
    @Test
    public void testGetEncryptedPassword() throws Exception {
        System.out.println("getEncryptedPassword");
        String password = "";
        byte[] salt = null;
        PasswordEncryptionService instance = new PasswordEncryptionService();
        byte[] expResult = null;
        byte[] result = instance.getEncryptedPassword(password, salt);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of generateSalt method, of class PasswordEncryptionService.
     *
    @Test
    public void testGenerateSalt() throws Exception {
        System.out.println("generateSalt");
        PasswordEncryptionService instance = new PasswordEncryptionService();
        byte[] expResult = null;
        byte[] result = instance.generateSalt();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    
}
