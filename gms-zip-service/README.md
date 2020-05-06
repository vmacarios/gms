# Generic Management System
# ZIP Microservice

To use this service, the first step is setup a mysql docker container.\
Assuming that the docker environment is already up and running, use the following command:\
`docker run --name=mysql -itdv ~/project/path:/mnt/host -e MYSQL_RANDOM_ROOT_PASSWORD=yes -p 3306:3306 mysql`

Some explanations:\
`--name` - define a name for the container\
`i` - keep STDIN open\
`t` - allocate a pseudo-TTY\
`d` - detached (run in background)\
`v` - bind a host path to the guest as a volume\
`e` - set environment variable\
`p 3306:3306` - map guest port to host port\
`mysql` - name of the desired image

Check if the container is running: `docker ps`\
Get the random password: `docker logs mysql 2>&1 | grep GENERATED`\
Access the container bash: `docker exec -it mysql /bin/bash`\
If necessary, the config file is located at /etc/mysql/my.cnf\
Access mysql client: `mysql -u root -p` (use the generated password)\
Change the password: `ALTER USER 'root'@'localhost' IDENTIFIED BY 'newpassword';`\
Create a new user: `CREATE USER 'newuser'@'%' IDENTIFIED BY 'user_password';`\
Grant privileges: `GRANT ALL PRIVILEGES ON *.* TO 'newuser'@'%';`\
Create the database: `CREATE DATABASE zip_db;`\
Select the database: `USE zip_db;`\
Use the provided zip_db.sql script to generate the table.

The table will be populated with the cep.la data.\
`http://cep.la/baixar\`\
The data is already included here in ZIP and TXT format.\
To import the data into the DB, the columns should have a common separator.\
Unfortunately, in this file, the state is separated from the city by "/".\
Therefore, it has to be converted to a tab (\t) character (which is the default separator in the file).\
Because may exist more than one "/" per line, a regex will be used.\
Navigate to the directory where the file is located and run:\
`awk -F "\t" '{sub(/\//,"\t",$2);print $1"\t"$2"\t"$3"\t"$4"\t"$5"\t"$6}' ceps.txt > ceps.tab`

A little explanation:\
`-F "\t"` - field separator. In this case is the tab (\t) character.\
`sub(/\//,"\t",$2)` - replace the first argument by the second, in the second column (the first argument is between two slash).\
`print $1"\t"$2"\t"$3` - print the columns.\
`ceps.txt > ceps.tab` - source and target.

For security reasons loading local data is disabled.\
It should be temporary enabled in both sides in order to allow the import.\
Otherwise, the following error will be displayed:\
`ERROR 3948 (42000): Loading local data is disabled; this must be enabled on both the client and server sides`

Enable it by adding an argument to mysql client command:\
`mysql -u root -p --local-infile`\
Then, do it on the server side:\
`SET GLOBAL local_infile=1;`\
Confirm that it's enabled:\
`SHOW VARIABLES LIKE 'local%';`\
And select the database:\
`USE zip_db;`

Now, use the following command to populated the table:\
`load data local infile 'ceps.tab'`\
`into table zip`\
`(@col1,@col2,@col3,@col4,@col5,@col6)`\
`set zip=@col1,address=@col5,comp=@col6,neighborhood=@col4,city=@col2,state=@col3,created_at=now();`

Confirm that the data was imported successfully:\
`SELECT * FROM zip;`

In case of truncated characters:\
`SET NAMES utf8;`

Finally, disable the local data loading:\
`SET GLOBAL local_infile=0;`

##Configuring LogBack (SLF4J)
Add `logback-classic` from `ch.qos.logback` to POM.xml\
Define the appenders and log level on resources/logback.xml\
Initialize the logger in the class using:\
`private static final Logger logger = LoggerFactory.getLogger(Zip.class);`

To send a non-error log by email, use:\
`Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");`

####Useful docker commands:

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
`docker volume prune` - remove all disconnected volumes

####Useful bash commands:

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


####Useful mysql commands:

`SHOW VARIABLES LIKE "variable";`\
`SET GLOBAL @@variable="";`\
`SHOW DATABASES;`\
`USE database;`\
`SHOW TABLES;`\
`EXPLAIN table;`\
`EXPLAIN query;`\
`SELECT * FROM table;`\
`SET NAMES utf8;`\
`SHOW GRANTS FOR 'user';`\
`GRANT ALL PRIVILEGES ON *.* TO 'newuser'@'%';`\
`REVOKE ALL PRIVILEGES ON database_name.* FROM 'database_user'@'localhost';`\
`CREATE USER 'newuser'@'%' IDENTIFIED BY 'user_password';`\
`ALTER USER 'newuser'@'IP_ADDRESS' IDENTIFIED BY 'user_password';`\
`DROP USER 'user'@'localhost'`


####Useful git commands:

`git add file` - add a file to be committed\
`git reset file` - remove the file from the commit\
`git commit -m"message"` - commit to the branch\
`git commit --amend` - edit the last commit message\
`git pull` - pull data from origin\
`git push origin branchName` - push data to origin\
`git checkout -b branchName` - connect to another branch\ 
`git status` - check which files will be committed\
`git branch` - check available branches\
`git log` - show commit history