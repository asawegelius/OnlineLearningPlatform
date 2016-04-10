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
import se.wegelius.olpstudenthandler.model.persistance.CourseBranchPersistance;
import se.wegelius.olpstudenthandler.model.persistance.CoursePersistance;
import se.wegelius.olpstudenthandler.model.persistance.CourseTypePersistance;

/**
 *
 * @author asawe
 */
public class CourseTypeDaoTest {

    private static CourseBranchPersistance branch;
    private static ContentProviderPersistance provider;
    private static CoursePersistance course;

    @BeforeClass
    public static void setUpClass() {
        branch = new CourseBranchPersistance();
        branch.setCourseBranchName("Test Branch");
        CourseBranchDao branchDao = new CourseBranchDao();
        branchDao.save(branch);
        provider = new ContentProviderPersistance();
        provider.setContentProviderName("testprovider");
        provider.setContentProviderEmail("test@test.dk");
        provider.setContentProviderDescription("expert in tests");
        ContentProviderDao providerDao = new ContentProviderDao();
        providerDao.save(provider);
        course = new CoursePersistance();
        course.setCourseName("Test Course");
        course.setCourseBranch(branch);
        course.setContentProvider(provider);
        CourseDao courseDao = new CourseDao();
        courseDao.save(course);
    }

    @AfterClass
    public static void tearDownClass() {
        CourseBranchDao branchDao = new CourseBranchDao();
        branchDao.delete(branch);
        CourseDao courseDao = new CourseDao();
        courseDao.delete(course);
        ContentProviderDao providerDao = new ContentProviderDao();
        providerDao.delete(provider);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        System.out.println("@After - tearDown");

        CourseTypeDao typeDao = new CourseTypeDao();
        for (CourseTypePersistance type : typeDao.getAll()) {
            switch (type.getCourseTypeName()) {
                case "Test save":
                    typeDao.delete(type);
                    System.out.println("deleting" + type.getCourseTypeName());
                    break;
                case "to be tested":
                    typeDao.delete(type);
                    System.out.println("deleting " + type.getCourseTypeName());
                    break;
                case "Test update":
                    typeDao.delete(type);
                    System.out.println("deleting " + type.getCourseTypeName());
                    break;
                case "test FindByID":
                    typeDao.delete(type);
                    System.out.println("deleting " + type.getCourseTypeName());
                    break;
                case "Test SaveOrUpdate":
                    typeDao.delete(type);
                    System.out.println("deleting " + type.getCourseTypeName());
                    break;
                default:
                    System.out.println("default " + type.getCourseTypeName());
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
        CourseTypePersistance courseType = new CourseTypePersistance();
        courseType.setCourseTypeName("Test save");
        courseType.setCtCourseBranchFk(branch.getCourseBranchId());
        CourseTypeDao typeDao = new CourseTypeDao();
        int sum = typeDao.count();
        typeDao.save(courseType);
        int newSum = typeDao.count();
        System.out.println("sum = " + sum + " newSum = " + newSum);
        assertTrue(sum < newSum);
    }

    /**
     * Test of update method, of class OlpDao.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        CourseTypeDao typeDao = new CourseTypeDao();
        CourseTypePersistance courseType = new CourseTypePersistance();
        courseType.setCourseTypeName("to be tested");
        courseType.setCtCourseBranchFk(branch.getCourseBranchId());
        typeDao.saveOrUpdate(courseType);
        courseType.setCourseTypeName("Test update");
        typeDao.update(courseType);
        CourseTypePersistance test = typeDao.findByID(courseType.getCourseTypeId());
        assertEquals(test.getCourseTypeName(), "Test update");
    }

    /**
     * Test of saveOrUpdate method, of class OlpDao.
     */
    @Test
    public void testSaveOrUpdate() {
        System.out.println("saveOrUpdate");
        CourseTypePersistance courseType = new CourseTypePersistance();
        courseType.setCourseTypeName("to be tested");
        courseType.setCtCourseBranchFk(branch.getCourseBranchId());
        CourseTypeDao typeDao = new CourseTypeDao();
        typeDao.save(courseType);
        CourseTypePersistance type2 = typeDao.findByID(courseType.getCourseTypeId());
        type2.setCourseTypeName("Test SaveOrUpdate");
        typeDao.saveOrUpdate(type2);
        assertEquals(typeDao.findByID(type2.getCourseTypeId()).getCourseTypeName(), typeDao.findByID(courseType.getCourseTypeId()).getCourseTypeName());
    }

    /**
     * Test of findByID method, of class OlpDao.
     */
    @Test
    public void testFindByID() {
        System.out.println("findByID");
        CourseTypePersistance courseType = new CourseTypePersistance();
        courseType.setCourseTypeName("test FindByID");
        courseType.setCtCourseBranchFk(branch.getCourseBranchId());
        CourseTypeDao typeDao = new CourseTypeDao();
        typeDao.save(courseType);
        Integer id = courseType.getCourseTypeId();
        Object expResult = id;
        Object result = typeDao.findByID(id).getCourseTypeId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAll method, of class OlpDao.
     */
    @Test
    public void testGetAll_0args() {
        System.out.println("getAll");
        CourseTypeDao instance = new CourseTypeDao();
        Set result = instance.getAll();
        assertEquals(instance.count(), result.size());
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
        CourseTypePersistance courseType = new CourseTypePersistance();
        courseType.setCourseTypeName("Test save");
        courseType.setCtCourseBranchFk(branch.getCourseBranchId());
        CourseTypeDao typeDao = new CourseTypeDao();
        int sum = typeDao.count();
        typeDao.save(courseType);
        typeDao.delete(courseType);
        int newSum = typeDao.count();
        assertEquals(sum, newSum);
    }

    /**
     * Test of getEntityClass method, of class OlpDao.
     */
    @Test
    public void testGetEntityClass() {
        System.out.println("getEntityClass");
        CourseTypeDao instance = new CourseTypeDao();
        Class expResult = CourseTypePersistance.class;
        Class result = instance.getEntityClass();
        assertEquals(expResult, result);
    }

}
