##1.	Solution Document History

###1.2	Revision History

| version   | Revision        | date    |	Implemented by| 
| --------- |:---------------:| :-----: |--------------:| 
|   1.0.0   | ch 5.6          |08-03-16 | Åsa Wegelius  |
|           |                 |         |               |
|           |                 |         |               |

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

_[Description of components involved and drawing of architecture]_
 
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

##6.	Impact on other system

_[How does the new system impact other system or infrastructure as the company.]_

##7.	Failover and scalability

_[How does the system scale and how does it handle failover.]_

###7.2	Technical implementation plan

_[How should the system be implemented with timeline.]_

###7.3	Solution implementation components (work breakdown structure)

_[What steps do you need to do to implement the product or project_
_eg._
####Preparation
1. _Analysis of requirements_
2. _..._
3. _Create installation manuals_
4. _Performance testing_

####Development of software
1. _Web service development_
2. _..._
3. _Frontend development_

####Hardware setup
1. _Install Hypervisor_
2. _Create VM’s for project_
3. _Install webservers and databases_
4. _SPT test of basic setup_
5. _...]_

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

