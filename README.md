## Here the Drive Link for Jar File

### [BookManagementSystemApplication Jar Files](https://drive.google.com/drive/folders/1533BNgAUHKpfal2l-Np3aKTwnE1y20HI?usp=sharing)

---

This file can be used as a template for initializing and running spring projects.

What's included:

1. Gradle file created from start.spring.io
2. Other dependencies like MySQL.
3. Spring Tests
4. CRUD operation on data

Usage -

1. To build the repository -

From the repository root,

1. run `./gradlew clean build -x test`run the build to create executable jar. The jar will be located inside build directories

## Authentication with Spring Security

This project utilizes Spring Security to handle authentication and authorization.

### API for Auth:

- /auth/login
- /auth/register

#### Public APIS

- /auth/login
- /auth/register
- /swagger-ui.html : for API documentation

## Need to be added:

Since this application uses cloud MySQL server from [Aiven](https://aiven.io) as database and I have removed mysql-config.properties file which contain hostname, portname, username, password, etc. of database, if you want to use mysql cloud you can use Aven.io add the file in main/resources
FileName should be exact same as I have provided this filename to LmsApplication.java as property file, if you wish to change it change in BookManagementSystemApplication.java too

#### mysql-config.properties

- mysql.host=hostname
- mysql.port=portname
- mysql.database=dbname
- mysql.username=username
- mysql.password=password
-

### application.properties

inside application.properties add your own secret key

- application.security.jwt.secret-key=YourSecretKey

## PostMan Collection

- [PostManCollection.json](https://github.com/kunaljs-sudo/BookRentalManagement/blob/main/BookManagementSystem.postman_collection.json)
