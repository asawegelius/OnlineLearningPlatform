/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olpstudenthandler.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;
import se.wegelius.olpstudenthandler.dao.ContentProviderDao;
import se.wegelius.olpstudenthandler.dao.CourseBranchDao;
import se.wegelius.olpstudenthandler.dao.CourseDao;
import se.wegelius.olpstudenthandler.dao.CourseTypeDao;
import se.wegelius.olpstudenthandler.model.persistance.ContentProviderPersistance;
import se.wegelius.olpstudenthandler.model.persistance.CourseBranchPersistance;
import se.wegelius.olpstudenthandler.model.persistance.CoursePersistance;
import se.wegelius.olpstudenthandler.model.persistance.CourseTypePersistance;

/**
 *
 * @author asawe
 */
public class CourseTest {
    private static CourseBranchPersistance branch;
    private static ContentProviderPersistance provider;
    private static CourseTypePersistance type;
    private static CoursePersistance course;
    
    public CourseTest() {
    }
    
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
        type = new CourseTypePersistance();
        type.setCourseTypeName("test type");
        type.setCtCourseBranchFk(branch.getCourseBranchId());
        CourseTypeDao typeDao = new CourseTypeDao();
        typeDao.save(type);
        course = new CoursePersistance();
        course.setCourseBranch(branch);
        course.setContentProvider(provider);
        course.setCourseType(type);
        System.out.println("courses branch is "+ course.getCourseBranch().getCourseBranchName());
        course.setCourseName("test save");
        CourseDao courseDao = new CourseDao();
        courseDao.save(course);
    }
    
    @AfterClass
    public static void tearDownClass() {
        CourseDao courseDao = new CourseDao();
        courseDao.delete(course);
        CourseBranchDao branchDao = new CourseBranchDao();
        ContentProviderDao providerDao = new ContentProviderDao();
        CourseTypeDao typeDao = new CourseTypeDao();
        branchDao.delete(branch);
        providerDao.delete(provider);
        typeDao.delete(type);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testConstructorWithPesistanceParameter(){
        CoursePersistance c = new CourseDao().findByID(2);
        System.out.println("courses branch is "+ c.getCourseBranch().getCourseBranchName());
        Course aCourse = new Course(c);
        String expectedName = c.getCourseBranch().getCourseBranchName();
        String requiredName = aCourse.getCourseBranch().getCourseBranchName();
        assertEquals(expectedName, requiredName);
    }
    
}
