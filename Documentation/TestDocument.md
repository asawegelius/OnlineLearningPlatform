##1.	Test Document History

###1.2	Revision History

| version   | Revision               | date    |	Implemented by| 
| --------- |------------------------| ------- |--------------| 
| 1.0       |  Added Junit tests     |14-05-16 |Åsa Wegelius   |
| 1.2       | Load, Stress, Spike, Soak Test|25-05-16|Åsa Wegelius|
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
    2.	Revision History 
    3.	Approvals  	
    4.	Distribution 
    5.	5 Confidentiality Rating
    6.	Link to online version
2.	Table of Contents  
3.	General
	1. Purpose
	2. Reasons
	3. 
4.  Load, Stress, Spike, Soak Test 
    2.  Test objectives   
    3.  General test conditions  
    4.  Tests performed 
        2.  Load tests 
        3.  Stress test
        4.  Spike test
        5.  Soak test
    5. Test results
        2. Load test
        3. Stress test
        4. Spike test
        5. Soak test
5.  User Acceptance Test
6.  Unit tests
 
## 3. General

### 1. Purpose

The Test Document presents the test scenario proposed to verify and validate the application functionalities according to the business requirements.
The test cases are designed having as starting point the User Stories. These test cases cover the applications functionality area and assure that the user stories are covered.
Another set of test cases cover the non-functional part of the application. These tests assure that the application is working under load and stress conditions. 
Due to dimension of the actual version of the application and the small number of users, the test scenarios will not address tests to validate the infrastructure reliability and redundancy or crash and disaster recovery.

Tests were split in four main areas thus they cover the main areas of the software testing process:
- 	Unit testing during the development process to assure that the main code sections are functional during the development sprints and in the deployment phases;;
- 	System Testing executed locally on the development environment to assure that the application is running in accordance with the development exit criteria;
- 	User Acceptance Testing (UAT) executed by third party team to assure that the application meets the business requirements;
- 	Performance Testing was executed to assure that the applications assure functionality in conditions of high usability and to determine what the conditions are when scalability should be implemented.


### 2. Reasons

The Tests series are mandatory to assure that the applications satisfy the customers need to use an online platform to improve and test their software knowledge.
 
## 4. Load, Stress, Spike, Soak Test
### Test objectives
Our goal is to find out if the web site meets the performance requirements as specified below.

The site needs to be able to handle 20000 unique visitors per day providing the industry standard level of service in terms of response time and error rate. Average response time should be less than 7 seconds and error rate should be less than 1%.

The assumtions for the load test is that qe expect the average number of simultaneous users will be about 100.
And that the average number of simultaneous users during peak hours will increase to 300.

### General test conditions
These general test conditions are appropriate for an average user:

1. Ask for all branches 
2. Ask for all courses
3. Select a course
4. Watch the course

### Tests performed
The folowing tests have been designed and executed
#### Load tests
#####OLPStudentHandler load test:

- 100 unique users
- 10 seconds ramp up period
- 10 loops/user
- request branches
- request courses
- request random course

### Test results
#### Load tests
#####OlpStudentHandler load test:
The test shows that the servers easily supports the demand of 100 simultanious users. 
![Graph of the result](http://wegelius.se/bilder/spring16/LoadGraph.png "Result Graph")

![Summary of the result](http://wegelius.se/bilder/spring16/LoadReport.png "Result Report")
The request for courses is significant higher, but within boundarys, than the other requests. It suggests there are room for investigatin and tweeking to increase performance.

![CPU use main server](http://wegelius.se/bilder/spring16/LoadMain.png "CPU main")
The main server have no problems with the workload of 100 users.

![CPU use seccondary server](http://wegelius.se/bilder/spring16/LoadSec.png "CPU seccondary")
The seccondary server have no problem with the workload of 100 users


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


