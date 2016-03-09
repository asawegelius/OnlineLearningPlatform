##1.	Solution Document History

###1.2	Revision History

| version   | Revision        | date    |	Implemented by| 
| --------- |:---------------:| :-----: |--------------:| 
|   1.0.0   | ch 5.6          |08-03-16 | Åsa Wegelius  |
|   1.0.1   | 5.3,5.4,6,7     |09-03-16 | Åsa Wegelius  |
|   1.0.2   | 5.8             |09-03-16 | Clovis Lebret |

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
18.	Cost  
  18.2	Platform cost  
  18.3	License and support  
  18.4	Operational Cost  
19. Risks  


##3.	General

_[General description of solution at a high level eg. Flow of the process you want to build a solution for, the sample below is for   medical transcription]_
 

###3.2	Solution summary

_[In short this project will …. improve/make it cheaper/give new options for …]_

###3.3	Deliverables summary

_[What this project will deliver that can be measured afterwards:_  
_Better..._  
-Improved..._  
_Cheaper...]_  

###3.4	Cost summary

_[High level cost elements that the project will carry:_ 

* _Hosting_
* _Software licenses_
* _Operations costs_
* _Software development hours_
* _…_

_High level cost elements that the project will not carry as it can use existing architecture..._

* _Share hosting with other projects_
* _…]_

##4.	Recommendation and next steps

_[Why should we do this project and when should it start. What needs to happen to do this project and what steps does the project involve.]_
##5.	Detailed solution description
_[Detailed description of the in the following sub sections]_

###5.2	Technical dictionary

_[Explain technical terms used so that the business can understand it.]_

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

_[Description of servers setup and sizing, include a drawing of setup
eg.]_

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

The setup to handle failovers is use of a synchronized backup server. We will use a service called [DNS Made Easy](http://www.dnsmadeeasy.com/) to monitor the server. Then it is just to write a simple script that checks your database too and make the service use the script to ensure that all is ok. It is a fairly straight forward solution. 

![Failover setup](http://wegelius.se/bilder/FailoverSetup.png "Failover setup")

###7.2	Technical implementation plan

_[How should the system be implemented with timeline.]_

###7.3	Solution implementation components (work breakdown structure)
![Total WBS](http://wegelius.se/bilder/OLP_WBS.jpg "total WBS")

#### Management
![Project Management](http://wegelius.se/bilder/PM_WBS.jpg "Project Management WBS")

#### Requirements
![Requirements](http://wegelius.se/bilder/Req_WBS.jpg "Requirements WBS")

#### Preparation
![Preparations](http://wegelius.se/bilder/Prep_WBS.jpg "Preparations")

#### Development
![Development](http://wegelius.se/bilder/Dev_WBS.jpg "Development")

#### Course Content
![Course Content](http://wegelius.se/bilder/Cont_WBS.jpg "Course Content")

##8.	Cost

_[What does the system cost to implement.]_

###8.2.	Platform cost

###8.3.	License and support 

###8.4.	Operational Cost

##9.	Risks

_[What risks are there in the project.]_

|Item#	    |Area       	|Description	     |Rank (RF=i*p)      |Mitigation		|Solution 		|
|-----------|:-----------------:|:------------------:|:-----------------:|:--------------------:|----------------------:|	
|           |                   |                    |                   |                      |                       |	
|           |                   |                    |                   |                      |                       |	
|           |                   |                    |                   |                      |                       |	
|           |                   |                    |                   |                      |                       |

