##1.	Test Document History

###1.2	Revision History

| version   | Revision               | date    |	Implemented by| 
| --------- |------------------------| ------- |--------------| 
| 1.0       |  Added Junit tests     |14-05-16 |Åsa Wegelius   |
|           |                        |         |               |
|           |                        |         |               |

###1.3	Approvals


| version   | Name            | title              |	Date    | 
| --------- |-----------------| ------------------ |----------| 
|           |                 |                    |          |
|           |                 |                    |          |
|           |                 |                    |          |

###1.4	Distribution

| version   | Name            | title              |	Date    | 
| --------- |-----------------| ------------------ |----------| 
|           |                 |                    |          |
|           |                 |                    |          |
|           |                 |                    |          |

###1.5	Confidentiality Rating

| Rating               |         | 
| -------------------- |:-------:|  
| Company Confidential |    x    | 
| Non Confidential     |         | 


##2.	Table of Contents

###Table of Contents
1.	Test Document History  
  1.2	Revision History  
  1.3	Approvals  
  1.4	Distribution 
  1.5 Confidentiality Rating
  1.6 Link to online version
2.	Table of Contents  
3.	General  
4.  Stress Performance Test
5.  User Acceptance Test
6.  Unit tests
  

## 6. Unit tests
We used unit tests to verify some crusial classes and methods like ensuring our generic dao setup works. Looking at the OLPStudentHandler we have over all 26 Junit tests: 
![Junit tests](http://wegelius.se/bilder/StudentHandlerJunitTests.png "Junit tests") 
Example of a Junit test of the dao class CourseDao:
```java
public class CourseDaoTest {    
    private static CourseBranchPersistance branch;
    private static ContentProviderPersistance provider;
    private static CourseTypePersistance type;
    
    public CourseDaoTest() {
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
    }
    
    @AfterClass
    public static void tearDownClass() {
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
        System.out.println("@After - tearDown");
        
        CourseDao courseDao = new CourseDao();
        for (CoursePersistance type : courseDao.getAll()) {
            switch (type.getCourseName()) {
                case "Test save":
                    courseDao.delete(type);
                    System.out.println("deleting" + type.getCourseName());
                    break;
                case "to be tested":
                    courseDao.delete(type);
                    System.out.println("deleting " + type.getCourseName());
                    break;
                case "Test update":
                    courseDao.delete(type);
                    System.out.println("deleting " + type.getCourseName());
                    break;
                case "test FindByID":
                    courseDao.delete(type);
                    System.out.println("deleting " + type.getCourseName());
                    break;
                case "Test SaveOrUpdate":
                    courseDao.delete(type);
                    System.out.println("deleting " + type.getCourseName());
                    break;
                default:
                    System.out.println("default " + type.getCourseName());
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
        CoursePersistance course = new CoursePersistance();
        course.setCourseBranch(branch);
        course.setContentProvider(provider);
        course.setCourseType(type);
        course.setCourseName("Test save");
        CourseDao courseDao = new CourseDao();
        int sum = courseDao.count();
        courseDao.save(course);
        int newSum = courseDao.count();
        System.out.println("sum = " + sum + " newSum = " + newSum);
        assertTrue(sum < newSum);
    }

    /**
     * Test of update method, of class OlpDao.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        CourseDao courseDao = new CourseDao();
        CoursePersistance course = new CoursePersistance();
        course.setCourseBranch(branch);
        course.setContentProvider(provider);
        course.setCourseType(type);
        course.setCourseName("to be tested");
        courseDao.saveOrUpdate(course);
        course.setCourseName("Test update");
        courseDao.update(course);
        CoursePersistance test = courseDao.findByID(course.getCourseId());
        assertEquals(test.getCourseName(), "Test update");
    }

    /**
     * Test of saveOrUpdate method, of class OlpDao.
     */
    @Test
    public void testSaveOrUpdate() {
        System.out.println("saveOrUpdate");
        CoursePersistance course = new CoursePersistance();
        course.setCourseBranch(branch);
        course.setContentProvider(provider);
        course.setCourseType(type);
        course.setCourseName("to be tested");
        CourseDao courseDao = new CourseDao();
        courseDao.save(course);
        CoursePersistance type2 = courseDao.findByID(course.getCourseId());
        type2.setCourseName("Test SaveOrUpdate");
        courseDao.saveOrUpdate(type2);
        assertEquals(courseDao.findByID(type2.getCourseId()).getCourseName(), courseDao.findByID(course.getCourseId()).getCourseName());
    }

    /**
     * Test of findByID method, of class OlpDao.
     */
    @Test
    public void testFindByID() {
        System.out.println("findByID");
        CoursePersistance course = new CoursePersistance();
        course.setCourseBranch(branch);
        course.setContentProvider(provider);
        course.setCourseType(type);
        course.setCourseName("test FindByID");
        CourseDao courseDao = new CourseDao();
        courseDao.save(course);
        Integer id = course.getCourseId();
        Object expResult = id;
        Object result = courseDao.findByID(id).getCourseId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAll method, of class OlpDao.
     */
    @Test
    public void testGetAll_0args() {
        System.out.println("getAll");
        CourseDao instance = new CourseDao();
        Set result = instance.getAll();
        assertEquals(instance.count(), result.size());
    }


    @Test
    public void testDelete() {
        System.out.println("delete");
        CoursePersistance course = new CoursePersistance();
        course.setCourseBranch(branch);
        course.setContentProvider(provider);
        course.setCourseType(type);
        course.setCourseName("Test delete");
        CourseDao courseDao = new CourseDao();
        int sum = courseDao.count();
        courseDao.save(course);
        courseDao.delete(course);
        int newSum = courseDao.count();
        assertEquals(sum, newSum);
    }

    /**
     * Test of getEntityClass method, of class OlpDao.
     */
    @Test
    public void testGetEntityClass() {
        System.out.println("getEntityClass");
        CourseDao courseDao = new CourseDao();
        Class expResult = CoursePersistance.class;
        Class result = courseDao.getEntityClass();
        assertEquals(expResult, result);
    }

    
}

```
Example of running the CourseDaoTest:
![CourseDao Junit test](http://wegelius.se/bilder/CourseDaoJunitTest.png "CourseDao Junit tests")


