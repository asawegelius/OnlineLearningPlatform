##1. SystemIntegrationReport Document History
###1.2	Revision History

| version   | Revision               | date    |	Implemented by| 
| --------- |------------------------| ------- |--------------| 
| 1.0       |  added part about ORM  |13-05-16 |  Åsa Wegelius |
| 1.1       |  added intro and a small part about jersey  |  28/05/2016       |  Clovis Lebret             |
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
1.	PMReport Document History  
  1.2	Revision History  
  1.3	Approvals  
  1.4	Distribution 
  1.5 Confidentiality Rating
2. Table of Contents  
3. Introduction
4.  Integration  
  4.2	Object Relational Mapping 
  4.3	Jersey Services   
5.	Integration patterns 
6. Conclusion
7. Appendices  
  7.2	References

###Table of Contents

##3. Introduction
This report documents the development process and our learning experiences over the course of implementing our project for the course _System Integration_ , as part of the Software Development education at The Copenhagen School of Design and Technology.

Through this report we explain how we implemented system integration techniques and technologies within the context of our project. In each section, we will provide a brief introduction to the theory behind the specific topic to give a better understanding of our reasoning and the choices we have made throughout the development, particularly in regards to our design and implementation as it relates to achieving particular integration goals, and what tradeoffs, if any, were made.
##4. Integration
###4.2 Object Relational Mapping
In object-oriented programming you work on Objects that are almost always non-scalar values. In a relational DBMS you store and manipulate scalar values in tables. To integrate you must either convert the objects to groups of simple values for storage and then convert back upon retrieval or you can use only simple scalar values within the program. Object-relational mapping implements the first approach. 

 ![Hibernate mapping](http://wegelius.se/bilder/hibernate_position.jpg "Hibernate mapping")

We use Hibernate ORM as framework. Hibernates core features are mapping from Java classes to database tables and from Java data types to SQL data types. Some of the advantages to use Hibernate: 
- It is database independent (no need for database specific queries and syntax). You use Hibernate Query Language (HQL). 
- You will get all advantages of OOP concepts like inheritance, encapsulation etc. 
- It provides caching mechanism (1st level & 2nd level cache) so you don't need to hit database for similar queries, you can cache it and use it from buffered memory to improve performance.
- It supports Lazy loading. That is, if parent class have n + 1 children and you only need information about one child there is no need to load the n children. Load only what you need. 

To get up and running with Hibernate you need a configuration file (hibernate.cfg.xml), a sessions factory and then you either use annotations for mapping or xml mapping files.
#####hibernate.cfg.xml:
```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC "-//Hibernate/Hibernate Configuration DTD 3.0//EN" "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
  <session-factory>
    <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
    <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/onlinelearningplatform</property>
    <property name="hibernate.connection.username">*****</property>
    <property name="hibernate.connection.password">******</property>
    <property name="hibernate.current_session_context_class">thread</property>
    <mapping resource="se/wegelius/olpstudenthandler/model/persistance/ContentProviderPersistance.hbm.xml"/>
    <mapping resource="se/wegelius/olpstudenthandler/model/persistance/CourseBranchPersistance.hbm.xml"/>
    <mapping resource="se/wegelius/olpstudenthandler/model/persistance/CoursePersistance.hbm.xml"/>
    <mapping resource="se/wegelius/olpstudenthandler/model/persistance/CourseTypePersistance.hbm.xml"/>
    <mapping resource="se/wegelius/olpstudenthandler/model/persistance/LecturePersistance.hbm.xml"/>
    <mapping resource="se/wegelius/olpstudenthandler/model/persistance/MultipleChoiceAnswerPersistance.hbm.xml"/>
    <mapping resource="se/wegelius/olpstudenthandler/model/persistance/MultipleChoiceOptionsPersistance.hbm.xml"/>
    <mapping resource="se/wegelius/olpstudenthandler/model/persistance/MultipleChoiceQuestionPersistance.hbm.xml"/>
    <mapping resource="se/wegelius/olpstudenthandler/model/persistance/VerificationtokenPersistance.hbm.xml"/>
    <mapping resource="se/wegelius/olpstudenthandler/model/persistance/UserPersistance.hbm.xml"/>
    <mapping resource="se/wegelius/olpstudenthandler/model/persistance/PlaylistPersistance.hbm.xml"/>
  </session-factory>
</hibernate-configuration>
```
#####sessions factory:
```java 
public class HibernateUtil {
    private static SessionFactory sessionFactory;
     
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            // loads configuration and mappings
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
             
            // builds a session factory from the service registry
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);           
        }
         
        return sessionFactory;
    }
}
```
#####xml mapping file:
```java 
<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
<!-- Generated May 10, 2016 4:15:18 PM by Hibernate Tools 4.3.1 -->
<hibernate-mapping>
    <class name="se.wegelius.olpstudenthandler.model.persistance.CourseTypePersistance" table="course_type" catalog="onlinelearningplatform">
        <id name="courseTypeId" type="java.lang.Integer">
            <column name="course_type_id" />
            <generator class="identity"></generator>
        </id>
        <property name="courseTypeName" type="string">
            <column name="course_type_name" length="45" unique="true" />
        </property>
        <property name="ctCourseBranchFk" type="java.lang.Integer">
            <column name="ct_course_branch_fk" />
        </property>
        <set name="courses" table="course" inverse="true" lazy="true" fetch="select">
            <key>
                <column name="c_course_type_fk" not-null="true" />
            </key>
            <one-to-many class="se.wegelius.olpstudenthandler.model.persistance.CoursePersistance" />
        </set>
    </class>
</hibernate-mapping>
```
#####persistance class:
```java
public class CourseTypePersistance  implements java.io.Serializable {


     private Integer courseTypeId;
     private String courseTypeName;
     private Integer ctCourseBranchFk;
     private Set<CoursePersistance> courses = new HashSet<CoursePersistance>(0);

    public CourseTypePersistance() {
    }

    public CourseTypePersistance(String courseTypeName, Integer ctCourseBranchFk, Set<CoursePersistance> courses) {
       this.courseTypeName = courseTypeName;
       this.ctCourseBranchFk = ctCourseBranchFk;
       this.courses = courses;
    }
   
    public Integer getCourseTypeId() {
        return this.courseTypeId;
    }
    
    public void setCourseTypeId(Integer courseTypeId) {
        this.courseTypeId = courseTypeId;
    }
    public String getCourseTypeName() {
        return this.courseTypeName;
    }
    
    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }
    public Integer getCtCourseBranchFk() {
        return this.ctCourseBranchFk;
    }
    
    public void setCtCourseBranchFk(Integer ctCourseBranchFk) {
        this.ctCourseBranchFk = ctCourseBranchFk;
    }
    public Set<CoursePersistance> getCourses() {
        return this.courses;
    }
    
    public void setCourses(Set<CoursePersistance> courses) {
        this.courses = courses;
    }
}
```
One advantage we got from using Hibernate was that we could write a generic dao. This cuts down the amount of dao code we need to write considerable. Most of it is reusable code so you can reap the benefits in future projects.

#####the generic interface
```java
public interface IOlpDao<T, ID extends Serializable> {
	Class<T> getEntityClass();
	T findByID(ID id);
	Set<T> getAll();
	Set<T> query(String hsql, Map<String, Object> params);
	int count();
	void save(T entity);
	void update(T entity);
	void saveOrUpdate(T entity);
	void merge(T entity);
	void delete(T entity);
}
```
#####example from the interface implementation:
```java
    @Override
    public void save(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error("tried to save " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * Find entities based on a query
     *
     * @param hsql the query
     * @param params the query parameters
     * @return a Set of the entities
     */
    @SuppressWarnings("unchecked")
    @Override
    public Set<T> query(String hsql, Map<String, Object> params) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<T> objects = null;
        logger.info("hsql: " + hsql + " params size: " + params.size());
        try {
            session.beginTransaction();
            Query query = session.createQuery(hsql);
            if (params != null) {
                for (String i : params.keySet()) {
                    query.setParameter(i, params.get(i));
                }
            }
            logger.info("query parameters: " + Arrays.toString(query.getNamedParameters()));
            if ((!hsql.toUpperCase().contains("DELETE"))
                    && (!hsql.toUpperCase().contains("UPDATE"))
                    && (!hsql.toUpperCase().contains("INSERT"))) {
                objects = query.list();
                logger.info("FINISHED - query. Result size=" + objects.size());
            } else {
                logger.info("FINISHED - query. ");
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                logger.error(e.getMessage());
            }
        }
        if (objects != null) {
            logger.info("no of objects: " + objects.size());
            return new HashSet<>(objects);
        }
        return null;
    }

```
#####example from CourseDao:
```java
public class CourseDao extends OlpDao<CoursePersistance, Integer> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CourseDao.class);

    public CourseDao(Class<CoursePersistance> type) {
        super(type);
    }

    /**
     *
     */
    public CourseDao() {
        super(CoursePersistance.class);
    }

    /**
     * Find an entity by its primary key
     *
     * @param id the entity's primary key
     * @return the entity
     */
    @SuppressWarnings("unchecked")
    @Override
    public CoursePersistance findByID(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CoursePersistance course = null;
        try {
            session.beginTransaction();
            course = (CoursePersistance) session.get(CoursePersistance.class, id);
            Hibernate.initialize(course.getCourseBranch());
            Hibernate.initialize(course.getContentProvider());
            Hibernate.initialize(course.getCourseType());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                logger.error(e.getMessage());
            }
        }
        return course;
    }
 .
 .
 .
}
```
You see how we only need to override the methods where the lazy fetch is "too lazy", where we need to initialize a child. 

###4.3 Jersey Services
 Jersey is the reference implementation for the JSR 311 specification.

The Jersey implementation provides a library to implement Restful webservices in our Java servlet container. On the server side Jersey provides the servlet implementation which scans predefined classes to identify RESTful resources. In our web.xml configuration file we registered this servlet for our web application. The Jersey implementation also provides a client library to communicate with the RESTful webservice.

The base URL of this servlet is: 
```
http://localhost:8080/OlpStudentHandler/rest/class_name/type_of_return/
```

Where the type of return can be either json, plain, or xml.

##5. Integration patterns

##6. Conclusion

##7. Appendices
###7.1 References
RedHat _Hibernate_ Retrieved Retrieved 05 13, 2016, from http://hibernate.org/orm/ 

Martin Fowler (8 May 2012) _OrmHate_  Retrieved 05 13, 2016, from http://martinfowler.com/bliki/OrmHate.html
