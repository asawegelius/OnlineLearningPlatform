##1.	Solution Document History

###1.2	Revision History

| version   | Revision               | date    |	Implemented by| 
| --------- |------------------------| :-----: |--------------| 
|   1.0.0   | ch 5.6                 |08-03-16 | Åsa Wegelius  |
|   1.0.1   | 5.3,5.4,6,7,3.4        |09-03-16 | Åsa Wegelius  |
|   1.0.2   | 5.8                    |09-03-16 | Clovis Lebret |
|   1.0.3   | 3.2,3.3,3.4,4, 5.2,5.4 |10-03-16 | Åsa Wegelius  |   

###1.3	Approvals

| version   | Revision        | date    |	Implemented by| 
| --------- |:---------------:| :-----: |--------------:| 
|           |                 |         |               |
|           |                 |         |               |
|           |                 |         |               |

###1.4	Distribution

| version   | Revision        | date    |	Implemented by| 
| --------- |:---------------:| :-----: |--------------:| 
|           |                 |         |               |
|           |                 |         |               |
|           |                 |         |               |

###1.5	Confidentiality Rating

| Rating               |         | 
| -------------------- |:-------:|  
| Company Confidential |    x    | 
| Non Confidential     |         | 


##2.	Table of Contents

###Table of Contents
1.	Solution Document History  
  1.2	Revision History  
  1.3	Approvals  
  1.4	Distribution  
2.	Table of Contents  
3.	General  
  3.2	Solution summary  
  3.3	Deliverables summary  
  3.4	Cost summary  
4.	Recommendation and next steps  
5.	Detailed solution description  
  5.2	Technical dictionary  
  5.3	Architecture overview  
  5.4	Server setup  
  5.5	Functional requirements  
  5.6	Non-functional requirements  
  5.7	Capacity recommendations  
6.	Impact on other system  
7.	Failover and scalability  
  7.2	Technical implementation plan  
  7.3	Solution implementation components (work breakdown structure)  
8. Risks  


##3.	General
The physical classroom is losing its monopoly as the only learning method, since the arrival of world wide web students can access information and learn from everywhere they are in the world just by having an internet connection and an online learning platform that help them learn long variety of subjects from economic to programming languages to philosophy and literature.  With online learning platform students can learn and implement their learning on their own pace and time.

In this project we will develop the backbone of an online education platform. It will support three roles, Admin, Content Provider and Student.  Admin administer the system, Content Provider produces and update courses and Student takes courses. The outcome of the project will be a prototype that fulfil this functions and can be extended to a further advanced learning platform.

###3.2	Solution summary
About 2 billion out of 7 billion humans speaks English today. A platform restricted to English rules out 5 billion humans.

Around 40% of the population have an internet access today. This is just those that have their own personal internet connection. There are other ways to access internet, e.g. from a friend’s home, from your job, from a cafe.

We will give all those that want to share their knowledge and all those that want to learn more a platform where they can connect. It will be designed to support an increasing amount of languages. 

The platform will be an online site with access to variation of courses on subjects which are introduced by experts in those areas. After going through each part of the course the student can review and exam himself or herself on the learning by going through multiple choice tests.  

People and enterprises interested in using the platform are paying a subscription per user which is going to be specified later on.

We will offer a platform that is:
* Easy to access. All with a browser and a login can use it.
* For those with knowledge they want to share but are not English speaking.
* For those that want to study but don’t know English.  


###3.3	Deliverables summary

* Browser interfaces for Students, Content Providers and Admin
* Web services for Students, Content Providers and Admin
* Databases for Users and Courses
 

###3.4	Cost summary

#####	Platform cost
The system will run on Wegelius private server at no cost. If or when the system will outgrow its current location is impossible to foresee and the future platform cost is therefore not feasible to tell.    

#####	License and support 
|Company           	                          |License/Service     |Amount/year         |
|--------------------------------------------:|:------------------:|-------------------:|	
| [DNS Made Easy](http://www.dnsmadeeasy.com/)|                    | $29.95             |	
|                                             |                    |                    |
|                                             |                    |                    |	
|                                             |                    |     Total: $29.95  |

#####	Operational Cost
|Operational Expense          |Amount/year                           |
|----------------------------:|-------------------------------------:|	
| Weekly maintenance          | 2 man-hours/week = 104               |	
| Monthly maintenance         | 4 man-hours/month = 48               |
| Quarterly maintenance       | 4 man-hours/quarter = 16             |	
|                             |                                      |
|                             |                                      |
|                             |            Total: 168 man-hours/year |


##4.	Recommendation and next steps
The idea with internet is that the more we share the smarter we get. This platform shares the same idea. All hardware is in place since we can share the setup SpeedVoter uses in the start-up phase. The only thing needed is the man-hour our team is prepared to deliver to make this idea come through. 

##5.	Detailed solution description

###5.2	Technical dictionary
|   Term      | Meaning                                                                                                         |  
| ----------- |:---------------------------------------------------------------------------------------------------------------:| 
| API         |Application programming interface                                                                                |
| DNS         | Domain Name System, a hierarchical decentralized naming system for computers, services or any resources connected to the internet |
| Failover    |A backup operation that automatically switches to a standby database, server or network if the primary system fails or is temporarily shut down for servicing.| 
|Load Balancer|Distributes workloads across multiple computing resources, such as computers, a computer cluster, network links, central processing units or disk drives.|
|Persistence  |What you save in a data storage to outlive the process that created it.|
|Rest API	    |An architectural style, and an approach to communications that is often used in the development of Web services  |
|Sharding     |A type of database partitioning that separates very large databases the into smaller, faster, more easily managed parts called data shards.|
|Web Service  |An application provides a service to other applications and communicates it via a defined protocol over internet.|

###5.3	Architecture overview
I will look at the architecture from three different perspectives. The structure of the applications, the structure of the system and the structure of a Tomcat cluster.

#### The application structure
The frontend is a browser interface, the View. The Rest API is the communication interface between the frontend and the backend. The backend have a controller layer and a model layer. The persistence objects is a subset of the model layer. Hibernate is the ORM, that is, handles the object-relational-mapping between the application and the relational database (MySQL).

![Application](http://wegelius.se/bilder/OLP.png "Application overview")

#### The system structure
The system consists of three separate restful services, one for Students, one for Content Providers and one for Administrators. 

![System](http://wegelius.se/bilder/Handlers.png "System overview")

#### Tomcat cluster
Tomcat have a recomended limit of 500 simultanious users. This is the perspective of if there is a need to handle more.

![Cluster](http://wegelius.se/bilder/LoadBalancer.png "cluster overview")

###5.4	Server setup
The system uses two databases, one with users and one with courses. The reason is that user information takes little space and can be stored at one location but courses contains videos which both require lots of space and as the product scales streaming uses an abnormal amount of bandwidth. So it will eventually require to be distributed, first to continent and then to countries.

The system is located at two servers, the primary server and the secondary (backup) server.

![Server setup](http://wegelius.se/bilder/FailoverSetup.png "Server setup")

###5.5	Functional requirements

_[What should the system be able to do. Behavior or functions of the system]_

###5.6	Non-functional requirements
####Usability
#####Color Palette for the User Interface:  
![Color palette for the user interface](http://wegelius.se/bilder/OnlineLearningPlatformPalette.jpg "Color Palette")
##### Training
A user shall not require any training to use the system. It shall be intuitive.
A power user (Content Provider) need to know how to create videos
#####Accessibility
Users and Power Users shall need a browser and an internet connection to access the system
##### Localization
The system shall support English, Danish, Swedish and Arabic

#### Reliability
##### Website Maintenance weekly check, downtime atm 2h
* All pages on the front end of the website are loading without errors
* Check all forms to ensure they are working properly
* Review and resolve any issues with emails sent from the web server
* Check and remove spam comments, form submissions and user accounts
* Check for any broken links
* Check for 404 errors and resolve these by fixing links or redirecting

##### Website Maintenance monthly check, downtime atm 6h
* Check website loading speed. If more than 3 seconds then tweek/check the server
* Review security scans and resolve any issues

##### Website Maintenance quarterly check, downtime atm 6h
* Test the website to ensure that it looks and displays properly on the most popular browsers and mobile devices
* Check backup health by restoring the most recent backup to a separate web server
* Check the uptime logs. If uptime is less than 99.9% then check/tweek the server

#### Supportability
##### Coding Standards
* [Java Coding Standards](http://www.oracle.com/technetwork/java/codeconvtoc-136057.html)
* [HTML(5) & CSS Style Guide and Coding Conventions](http://www.w3schools.com/html/html5_syntax.asp)
* [JavaScript Style Guide and Coding Conventions](http://www.w3schools.com/js/js_conventions.asp)
* [CSS and LESS Coding Standards](http://www.runopencode.com/index.php/how-we-code/css-and-less-coding-standards)

#### Design Constraints
#####	Frontend
The web-based interface shall run on Explorer, Chrome, Safari.

Languages: JSP, HTML5, JavaScript, CSS, LESS

#####	Backend
Java, Java Servlet API

#####	Server
Apache Tomcat 8

#####	Database
MySQL

###5.7	Capacity recommendations

_[How does the system scale and how do we measure it under SPT.]_

When manipulating scalable system, it's important to take in consideration the available capacity on the long term. Since we will be using a mysql database as a storage solution, we will be provided options that limit the resources used by the backup process, in order to minimize backup overhead for busy or huge databases, or specify behaviors of the process if he has to encounter resource issues. On the future we might also imagine partitioning/sharding the database to additional server, making sure the system sustain.

As for the capacity planning, it's very difficult to answer such a vast question of scalability. We might consider using MySQL Cluster, which is a highly scalable, real-time and provides automatic data partitioning with load balancing.

##6.	Impact on other system
__SpeedVoter__ are installed on the same hardware server but runs in a different web server. Speedvoter is an e-voting system with about 300 000 users. We will continuous monitor the performance to see that the server capacity can handle both. 

##7.	Failover and scalability

The setup to handle failovers is use of a synchronized backup server. We will use a service called [DNS Made Easy](http://www.dnsmadeeasy.com/) to monitor the server. It will check a script we setup and redirect to the secondary server if it fails. 

![Failover setup](http://wegelius.se/bilder/FailoverSetup.png "Failover setup")

###7.2	Technical implementation plan

_[How should the system be implemented with timeline.]_

###7.3	Solution implementation components (work breakdown structure)
![Total WBS](http://wegelius.se/bilder/WBS_OLP.png "total WBS")

#### Management
![Project Management](http://wegelius.se/bilder/PM_WBS.png "Project Management WBS")

#### Requirements
![Requirements](http://wegelius.se/bilder/Req_WBS.png "Requirements WBS")

#### Preparation
![Preparations](http://wegelius.se/bilder/Prep_WBS.png "Preparations")

#### Development
![Development](http://wegelius.se/bilder/Dev_WBS.png "Development")

#### Course Content
![Course Content](http://wegelius.se/bilder/Cont_WBS.png "Course Content")

##8.	Risks

_[What risks are there in the project.]_

|Item#	    |Area       	|Description	     |Rank (RF=i*p)      |Mitigation		|Solution 		|
|-----------|:-----------------:|:------------------:|:-----------------:|:--------------------:|----------------------:|	
|           |                   |                    |                   |                      |                       |	
|           |                   |                    |                   |                      |                       |	
|           |                   |                    |                   |                      |                       |	
|           |                   |                    |                   |                      |                       |

