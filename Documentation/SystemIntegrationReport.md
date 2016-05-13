##1. SystemIntegrationReport Document History
###1.2	Revision History

| version   | Revision               | date    |	Implemented by| 
| --------- |------------------------| ------- |--------------| 
| 1.0       |  added part about ORM  |13-05-16 |  Åsa Wegelius |
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
1.	PMReport Document History  
  1.2	Revision History  
  1.3	Approvals  
  1.4	Distribution 
  1.5 Confidentiality Rating
2. Table of Contents  
3. Introduction
4.  Integration
5.	Integration patterns 
6. Conclusion
7. Appendices  
  7.2	References

###Table of Contents

##3. Introduction

##4. Integration
###4.2 Object Relational Mapping
In object-oriented programming you work on Objects that are almost always non-scalar values. In a relational DBMS you store and manipulate scalar values in tables. To integrate you must either convert the objects to groups of simple values for storage and then convert back upon retrieval or you can use only simple scalar values within the program. Object-relational mapping implements the first approach. 

 ![Hibernate mapping](http://wegelius.se/bilder/hibernate_position.jpg "Hibernate mapping")

We use Hibernate ORM as framework. Hibernates core features are mapping from Java classes to database tables and from Java data types to SQL data types. Some of the advantages to use Hibernate: 
- it is database independent (no need for database specific queries and syntax). You use Hibernate Query Language (HQL). 
- you will get all advantages of OOP concepts like inheritance, encapsulation etc, 
- it provides caching mechanism (1st level & 2nd level cache) so you don't need to hit database for similar queries, you can cache it and use it from buffered memory to improve performance.
- it supports Lazy loading. That is, if parent class have n + 1 children and you only need information about one child there is no need to load the n children. Load only what you need. 

To get up and running with Hibernate you need a configuration file (hibernate.cfg.xml), a sessions factory and then you either use annotations for mapping or xml mapping files.
#####hibernate.cfg.xml
```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC "-//Hibernate/Hibernate Configuration DTD 3.0//EN" "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
  <session-factory>
    <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
    <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/onlinelearningplatform</property>
    <property name="hibernate.connection.username">olp</property>
    <property name="hibernate.connection.password">olppassword1</property>
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
#####sessions factory
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
#####xml mapping file
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
##5. Integration patterns

##6. Conclusion

##7. Appendices
###7.1 References
RedHat _Hibernate_ Retrieved Retrieved 05 13, 2016, from http://hibernate.org/orm/ 

Martin Fowler (8 May 2012) _OrmHate_  Retrieved 05 13, 2016, from http://martinfowler.com/bliki/OrmHate.html
