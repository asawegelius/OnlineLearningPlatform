/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.dao;

import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.wegelius.olpstudenthandler.model.persistance.ContentProviderPersistance;

/**
 *
 * @author asawe
 */
public class ContentProviderDaoTest {
    
    public ContentProviderDaoTest() {
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
        System.out.println("@After - tearDown");
        
        ContentProviderDao providerDao = new ContentProviderDao();
        for (ContentProviderPersistance provider : providerDao.getAll()) {
            switch (provider.getContentProviderName()) {
                case "Test save":
                    providerDao.delete(provider);
                    System.out.println("deleting" + provider.getContentProviderName());
                    break;
                case "to be tested":
                    providerDao.delete(provider);
                    System.out.println("deleting " + provider.getContentProviderName());
                    break;
                case "Test update":
                    providerDao.delete(provider);
                    System.out.println("deleting " + provider.getContentProviderName());
                    break;
                case "test FindByID":
                    providerDao.delete(provider);
                    System.out.println("deleting " + provider.getContentProviderName());
                    break;
                case "Test SaveOrUpdate":
                    providerDao.delete(provider);
                    System.out.println("deleting " + provider.getContentProviderName());
                    break;
                case "Test delete":
                    providerDao.delete(provider);
                    System.out.println("deleting " + provider.getContentProviderName());
                    break;
                default:
                    System.out.println("default " + provider.getContentProviderName());
                    break;
            }
        }
    }
   /**
     * Test of save method, of class OlpDao.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        ContentProviderPersistance provider = new ContentProviderPersistance();
        provider.setContentProviderName("Test save");
        ContentProviderDao providerDao = new ContentProviderDao();
        int sum = providerDao.count();
        providerDao.save(provider);
        int newSum = providerDao.count();
        System.out.println("sum = " + sum + " newSum = " + newSum);
        assertTrue(sum < newSum);
    }

    /**
     * Test of update method, of class OlpDao.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        ContentProviderPersistance provider = new ContentProviderPersistance();
        provider.setContentProviderName("to be tested");
        ContentProviderDao providerDao = new ContentProviderDao();
        providerDao.saveOrUpdate(provider);
        provider.setContentProviderName("Test update");
        providerDao.update(provider);
        ContentProviderPersistance test = providerDao.findByID(provider.getContentProviderId());
        assertEquals(test.getContentProviderName(), "Test update");
    }

    /**
     * Test of saveOrUpdate method, of class OlpDao.
     */
    @Test
    public void testSaveOrUpdate() {
        System.out.println("saveOrUpdate");
        ContentProviderPersistance provider = new ContentProviderPersistance();
        provider.setContentProviderName("to be tested");
        ContentProviderDao providerDao = new ContentProviderDao();
        providerDao.save(provider);
        ContentProviderPersistance type2 = providerDao.findByID(provider.getContentProviderId());
        type2.setContentProviderName("Test SaveOrUpdate");
        providerDao.saveOrUpdate(type2);
        assertEquals(providerDao.findByID(type2.getContentProviderId()).getContentProviderName(), providerDao.findByID(provider.getContentProviderId()).getContentProviderName());
    }

    /**
     * Test of findByID method, of class OlpDao.
     */
    @Test
    public void testFindByID() {
        System.out.println("findByID");
        ContentProviderPersistance provider = new ContentProviderPersistance();
        provider.setContentProviderName("to be tested");
        ContentProviderDao providerDao = new ContentProviderDao();
        providerDao.save(provider);
        Integer id = provider.getContentProviderId();
        Object expResult = id;
        Object result = providerDao.findByID(id).getContentProviderId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAll method, of class OlpDao.
     */
    @Test
    public void testGetAll_0args() {
        System.out.println("getAll");
        ContentProviderDao providerDao = new ContentProviderDao();
        Set result = providerDao.getAll();
        assertEquals(providerDao.count(), result.size());
    }

    /**
     * Test of getAll method, of class OlpDao.
     *
     * @Test public void testGetAll_String() { System.out.println("getAll");
     * String query = "WHERE ct_course_branch_fk < 25"; CourseTypeDao typeDao =
     * new CourseTypeDao(); Set all = typeDao.getAll(query); for
     * (CourseTypePersistance type : typeDao.getAll(query)) { assertTrue(
     * type.getCtCourseBranchFk()< 25); } }
     */
    /**
     * Test of getAll method, of class OlpDao.
     *
     * @Test public void testGetAll_String_ObjectArr() {
     * System.out.println("getAll"); String queryString = ""; Object[] params =
     * null; CourseTypeDao instance = new CourseTypeDao(); Set expResult = null;
     * Set result = instance.getAll(queryString, params);
     * assertEquals(expResult, result); // TODO review the generated test code
     * and remove the default call to fail. fail("The test case is a
     * prototype."); }
     */
    /**
     * Test of delete method, of class OlpDao.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        ContentProviderPersistance contentProvider = new ContentProviderPersistance();
        contentProvider.setContentProviderName("Test delete");
        ContentProviderDao providerDao = new ContentProviderDao();
        int sum = providerDao.count();
        providerDao.save(contentProvider);
        providerDao.delete(contentProvider);
        int newSum = providerDao.count();
        assertEquals(sum, newSum);
    }

    /**
     * Test of getEntityClass method, of class OlpDao.
     */
    @Test
    public void testGetEntityClass() {
        System.out.println("getEntityClass");
        ContentProviderDao providerDao = new ContentProviderDao();
        Class expResult = ContentProviderPersistance.class;
        Class result = providerDao.getEntityClass();
        assertEquals(expResult, result);
    }

}
