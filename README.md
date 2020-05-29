# Generic Management System

This system consist in several microservices connected.\
Those microservices use RESTful API to communicate.\
It's based on Docker containers.\
The technologies used are:
- MySQL
- Java / Spring
- JUnit 5 / Mockito
- Angular

The microservices are:

- ZIP Service
- Registry Service
- Subscription Service
- Reports Service
- Mailing Service
- Finance Service 

##Configuring LogBack (SLF4J)
Add `logback-classic` from `ch.qos.logback` to POM.xml\
Define the appenders and log level on resources/logback.xml\
Initialize the logger in the class using:\
`private static final Logger logger = LoggerFactory.getLogger(Zip.class);`

To send a non-error log by email, use:\
`Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");`

##Configuring SpringBoot
The initial project can be configured in the following address:\
`https://start.spring.io/`\
Add the necessary dependencies and insert them on the POM.xml\
Adjust database connection settings in application.properties file

##Useful Commands
####Useful docker commands

`docker run` - pull and run an image\
`docker ps -a` - show all containers\
`docker inspect` - show technical info about the mentioned container\
`docker start|stop` - start or stop a container\
`docker exec` - execute a command in mentioned container\
`docker logs` - show the log for the container\
`docker rename` - rename a container\
`docker cp` - copy files between host and guest\
`docker rm -v` - delete a container (-v also remove its associated volume)\
`docker system prune` - purge all stopped containers\
`docker images -a` - show all images\
`docker rmi` - remove a image\
`docker volume ls -f dangling=true` - list all volumes not connected to any container\
`docker volume prune` - remove all disconnected volumes\
`docker-machine` - control the VM status

####Useful bash commands

`lsof -i :port` - list ports in use (or open files if -i is suppressed)\
`du -h folder` - display disk usage for the folder\
`df -h` - display free disk space\
`tr -s 'char1' 'char2' < file > newfile` - replace char1 by char2 (`-s` means that if exists more than one occurrence it will be treated as only one)\
`time` - measure the execution time\
`wc -l` - word count (lines)\
`cut -f1` - print only the column number of the files\
`sort -n` - order the list\
`tail -n 1` - shows only the last value\
`nc -vz ip port` - connection listener

####Useful mysql commands

`SHOW VARIABLES LIKE "variable";`\
`SET GLOBAL @@variable="";`\
`SHOW DATABASES;`\
`USE database;`\
`SHOW TABLES;`\
`EXPLAIN table;`\
`EXPLAIN query;`\
`SELECT * FROM table;`\
`SELECT * FROM table ORDER BY id DESC LIMIT 1;`\
`SET NAMES utf8;`\
`SHOW GRANTS FOR 'user';`\
`GRANT ALL PRIVILEGES ON *.* TO 'newuser'@'%';`\
`REVOKE ALL PRIVILEGES ON database_name.* FROM 'database_user'@'localhost';`\
`CREATE USER 'newuser'@'%' IDENTIFIED BY 'user_password';`\
`ALTER USER 'newuser'@'IP_ADDRESS' IDENTIFIED BY 'user_password';`\
`ALTER TABLE tableName RENAME COLUMN column1 TO column2;`\
`DROP USER 'user'@'localhost'`

####Useful git commands

`git add file` - add a file to be committed\
`git reset file` - remove the file from the commit\
`git commit -m"message"` - commit to the branch\
`git commit --amend` - edit the last commit message\
`git pull` - pull data from origin\
`git push origin branchName` - push data to origin\
`git checkout -b branchName` - connect to another branch\
`git status` - check which files will be committed\
`git branch` - check available branches\
`git log` - show commit history\
`git stash` - save the current working contents (then change to the desired branch)\
`git stash pop` - get the stashed content (look for conflicts)