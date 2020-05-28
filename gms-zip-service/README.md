# Generic Management System
# ZIP Microservice

This microservice is part of the GMS.\ 
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
`set zip=@col1,address=@col5,comp=@col6,neighborhood=@col4,city=@col2,state=@col3,createdAt=now();`

Confirm that the data was imported successfully:\
`SELECT * FROM zip;`

In case of truncated characters:\
`SET NAMES utf8;`

Finally, disable the local data loading:\
`SET GLOBAL local_infile=0;`

##Code

###Controller Test
When using JUnit 4, the annotation `@RunWith(SpringRunner.class)` should be used.\
It was replace by `@ExtendWith(SpringExtension.class)` in JUnit 5 (jupiter).\
Use `@WebMvcTest` to test just the web layer or `@SpringBootTest` to test in a Spring context.\
MockMvc is auto-configured in the first case. For the second case, use `@AutoConfigureMockMvc`.\
To mock a dependency, use `@Mock`. Use `@MockBean` instead when in a Spring context.

To test the `findAll` method a list should be created and some zips added to it.\
Then, using the `when` method, return the mocked result list from `zipService.findAll()` method.\
The next step is to use the `mockMvc.perform()` and perform a get request.\
Using `jsonPath`, compare the result size (`$` means the JSON root).\
Check if the service was called just once and that there was no more interactions.\
Finally, test for Not Found URI.

The second test is the `save` method.\
The mocked service returns a zip object.\
Using an `@Autowired ObjectMapper` it is converted to a JSON as string.\
Date fields should be formatted using the following line in the model class:\
`@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")`\
Use the `MockMvc` to perform a POST action and assign it to a `ResultAction`.\
Then, assert the result.

The method `getOneZip` is should be tested for a result or not found.

The tests for `delete` checks if the returned code is 204 (no content) or 404 (not found).

###Controller
The ZipService was injected using the constructor injection.\
The URI for `findAll` method was defined using `@GetMapping`\
The result can be returned in two ways (both are in the code for studies):\
`@ResponseBody` - which use the return as body.\
`ResponseEntity<T>` - which allow to modify body, headers and status code.

For the `save` method the URI is defined with `@PostMapping`\
The zip object is passed as argument using `@RequestBody`\
Return a `ResponseEntity` with the save method from the service and the `HttpStatus.CREATED`.

To `getOneZip` the id is passed as argument using `@PathVariable`.

`delete` method returns status as 204 (no content) if it successful delete the object.
Or catches `EmptyResultDataAccessException`, log it and return 404 (not found).

###Service Test
To test the service, the `@SpringBootTest` is used.\
The repository is mocked using `@MockBean`.\
Then a list is created so the mock can return some data.\
Use the following line to retrieve the data from the mocked repository:\
`when(zipRepository.findAll()).thenReturn(zipList);`\
Finally compare the data retrieved from the mocked repository.

To test save and/or delete method, just verify if the mocked repository was called and it was just once.

FindById method can receive a Zip object. It should be tested if is present.
If it is present, check if the result is the same as requested.

###Service Integration Test
This test use the real service class (`@Autowired`) to test the access to the repository.\
Create a Zip item, save it to the repository and check if it was successfully saved.
To avoid the use of a real DB instance, an application.properties file can be configured for the test context.
H2 embedded DB will be used in this case:
`spring.datasource.driver-class-name=org.h2.Driver`
`spring.datasource.url=jdbc:h2:mem:zip_db;MODE=MySQL;DB_CLOSE_DELAY=-1`
`spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect`
`spring.jpa.properties.hibernate.hbm2ddl.auto=create`

Some objects are created in the `@BeforeAll` method.
Then save one of them to DB `@BeforeEach` one. 
Use `@Transacional` in the methods which changes DB to roll back to it previously state.

###Service
The ZipRepository was injected using the constructor injection.\
Use the repository to call the  methods.