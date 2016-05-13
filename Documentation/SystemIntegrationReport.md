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

We use Hibernate ORM as framework. Hibernates core features are mapping from Java classes to database tables and from Java data types to SQL data types 

##5. Integration patterns

##6. Conclusion

##7. Appendices
###7.1 References
[We primary used Hibernates homepage to find information about Hibernate](http://hibernate.org/orm/ "Hibernate's Homepage")
