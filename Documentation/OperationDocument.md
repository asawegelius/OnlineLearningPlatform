##1.	Operation Document History

###1.2	Revision History

| version   | Revision               | date    |	Implemented by| 
| --------- |------------------------| ------- |--------------| 
| 1.0       | Master Slave replica on MySQL|15-05-16 |Åsa Wegelius  |
| 1.1       |Tomcat cluster          |20-05-16 | Åsa Wegelius  |
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
1.	Operational Document History  
  1.2	Revision History  
  1.3	Approvals  
  1.4	Distribution 
  1.5 Confidentiality Rating
  1.6 Link to online version
2.	Table of Contents  
3.	General  
4.  Architecture Overview
5.	Installation Manual   
  5.2	Master Slave replica on MySQL  
  5.3	Tomcat cluster 
6.  Operation Manuel
7.  Troubleshooting guide
8.  Traceability Matrix
9.	Appendices  
  9.2	References  



##5. Installation Manual
###5.2  Master Slave replica on MySQL
####To configure some basic options:
You master need to be set to binary logging and establish a unique server ID. The binary log is the basis for replicating changes from the master to its slaves.

1. Shut down the server. Find the __my.cnf__ or __my.ini__ file. Find the section that starts with __[mysqld]__. The options you shall set or add are __log-bin__ and __server-id__. If they are commented out, just uncomment them and alter the values to your choises, if they are not there you add them. You can see how it looks below with a logfile name prefix of mysql-bin and server-id 1. After it is set you need to restart the server.
```mysql
[mysqld]
log-bin=mysql-bin
server-id=1
```
2. Your slave or slaves must have a unique id that differs from the master and other slaves. As with the master, find the __my.cnf__ or __my.ini__ file and the section that starts with __[mysqld]__. Set the server-id to an unique id. You can see how it looks below with a server-id 2. After it is set you need to restart the server.
```mysql
[mysqld]
server-id=2
```
3. It is not necessary to set the log-bin on slaves but if you do you can use the slave's binary log for data backups and crash recovery.

You need to create a slave user for each slave and give them REPLICATION SLAVE privilege on the server. For example, if you want to set up a new user, repl, that can connect for replication from any host within the mydomain.com domain, you can do as below:
```mysql
mysql> CREATE USER 'repl'@'%.mydomain.com' IDENTIFIED BY 'slavepass';
mysql> GRANT REPLICATION SLAVE ON *.* TO 'repl'@'%.mydomain.com';
```
4. You need a fresh starting point on your master before starting to replicate to avoid inconsistency and corrupted data. You need to give the master binary log coordinates to the slave so it starts the replication at the correct point. 

Start a session on the master by connecting to it with the command-line client, and flush all tables and block write statements by executing the FLUSH TABLES WITH READ LOCK statement as below:
```mysql
mysql> FLUSH TABLES WITH READ LOCK;
```
5. In a different session on the master, use the SHOW MASTER STATUS statement to determine the current binary log file name and position as below:
```mysql
mysql > SHOW MASTER STATUS;
+------------------+----------+--------------+------------------+
| File             | Position | Binlog_Do_DB | Binlog_Ignore_DB |
+------------------+----------+--------------+------------------+
| mysql-bin.000003 | 73       | test         | manual,mysql     |
+------------------+----------+--------------+------------------+
```
6. You find the name of the log file in the file column and the position in the log file in position column. These are the values you need when you set up the slave since they tell the slave where to start the replica. If the columns are empty the values that you need to use later when specifying the slave's log file and position are the empty string ('') and 4.

You might already have data on your master and need to syncronize your slave. You can use the mysqldump tool to create a dump of all the databases you want to replicate. To dump all databases into a file named __dbdump.db__ and include the --master-data that appends the CHANGE MASTER TO statement required on the slave to start the replication process you do as below:
```mysql
shell> mysqldump --all-databases --master-data > dbdump.db
```
To import the data, either copy the dump file to the slave, or access the file from the master when connecting remotely to the slave.

7. The slave need connection information to be able to communicate with the master. To set the master configuration on the slave you execute the below statements after you swap the values with the correct values for your system:
```mysql
mysql> CHANGE MASTER TO
    ->     MASTER_HOST='master_host_name',
    ->     MASTER_USER='replication_user_name',
    ->     MASTER_PASSWORD='replication_password',
    ->     MASTER_LOG_FILE='recorded_log_file_name',
    ->     MASTER_LOG_POS=recorded_log_position;b
```

8.  If you are setting up a new replica enviroment using data from a different MySQL server, run the dump file from that one on the new master as below. The updates are automatically propagated to the slaves.
```mysql
shell> mysql -h master < fulldb.dump
```
9.  If you are setting up replication with existing data there are two ways. If you used __mysqldump__ start the slave using the  --skip-slave-start option so that replication does not start. Then you import the dump file as below:
```mysql
shell> mysql < fulldb.dump
```
If you created a snapshot using the raw data files you extract the data files into your slave data directory. You can do as below:
```mysql
shell> tar xvf dbdump.tar
```
You may need to set permissions and ownership on the files so that the slave server can access and modify them. Start the slave using the  --skip-slave-start option so that replication does not start. Configure the slave with the replication coordinates from the master. This tells the slave the binary log file and position within the file where replication needs to start. Also, configure the slave with the login credentials and host name of the master. Start the slave threads as below:
```mysql
mysql> START SLAVE;
```
After you have performed this procedure, the slave connects to the master and replicates any updates that have occurred on the master since the snapshot was taken.

###Tomcat cluster

#### Install Apache HTTP Server on Windows
The Apache HTTP Server Project itself does not provide binary releases, only source code. But there are binary distributions available on the Internet. You can find one at [Apache Haus](http://www.apachehaus.com/cgi-bin/download.plx "Binary").
#####Steps after downloading the binary:
- Ensure you have Visual C++ 2008 Redistributable Package. If not, you need to install it. You can find it at [Microsoft](https://www.microsoft.com/en-us/download/details.aspx?id=48145 "Visual C++ 2008 Redistributable Package").
- Unzip the Apache24 folder in the package zip file to the root directory on any drive. Example: c:\Apache24.
- Open a command prompt window and cd to the \Apace24\bin folder on the drive you unzipped the zip file to. To Start Apache in the command prompt type: _httpd.exe_
- This distribution comes pre-configured for localhost. You can now test your installation by opening up your Web Browser and typing in the address: _http://localhost_. If everything is working properly you should see the Apache Haus's test page.
- You can shut down Apache by pressing Ctrl+C (It may take a few seconds)

####Install Apache HTTP Server as service
In most cases you will want to run Apache as a Windows Service. 
To do so you install Apache as a service by typing at the command prompt [1];

_httpd -k install_
You can then start Apache by typing

_httpd -k start_
Apache will then start and eventually release the command prompt window.


##9.  Appendices
###9.2 References
MySQL Manual (version 5.6) _18.1.2.1 Setting the Replication Master Configuration_  Retrieved 05 15, 2016, from https://dev.mysql.com/doc/refman/5.6/en/replication-howto-masterbaseconfig.html

MySQL Manual (version 5.6) _17.1.1.2 Setting the Replication Slave Configuration_ Retrieved 05 15, 2016, from https://dev.mysql.com/doc/refman/5.6/en/replication-howto-slavebaseconfig.html

MySQL Manual (version 5.6) _17.1.1.3 Creating a User for Replication_ Retrieved 05 15, 2016, from https://dev.mysql.com/doc/refman/5.6/en/replication-howto-repuser.html

MySQL Manual (version 5.6) _17.1.1.4 Obtaining the Replication Master Binary Log Coordinates_ Retrieved 05 15, 2016, from https://dev.mysql.com/doc/refman/5.6/en/replication-howto-masterstatus.html

MySQL Manual (version 5.6) _17.1.1.5 Creating a Data Snapshot Using mysqldump_ Retrieved 05 15, 2016, from https://dev.mysql.com/doc/refman/5.6/en/replication-howto-mysqldump.html

MySQL Manual (version 5.6) _17.1.1.10 Setting the Master Configuration on the Slave_ Retrieved 05 15, 2016, from https://dev.mysql.com/doc/refman/5.6/en/replication-howto-slaveinit.html

MySQL Manual (version 5.6) _18.1.2.5 Setting Up Replication Slaves_ Retrieved 05 16, 2016, from http://dev.mysql.com/doc/refman/5.7/en/replication-setup-slaves.html

Apache _Apache HTTP Server Version 2.4_ Retrieved 05 20, 2016, from https://httpd.apache.org/docs/2.4/platform/windows.html

