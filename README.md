#### ITT WEB

This is the Web backend for the [ITT-Problem](https://github.com/Nyariki/ITT-PROBLEM/). The Web platform is written in Spring. It is divided into 3 configurable modules.

##### 1. [DB](db)

The DB modules uses [Mybatis Migrations](https://mybatis.org/migrations/) to manage a MYSQL database. The migrations are run via mysql scripts in the [scripts](db/migrations/scripts).

1. Point the db [environment](db/migrations/environments/development.properties) file to your ```mysql``` instance.
In my case it's a local database instance called ```itt``` and running on port ```3306```. My username is ```root``` and my password is ```root```.

2. Follow [this ](db/README.md) guide to set up the mybatis migrations. Alternatively you can navigate to the [bin](db/bin/) folder and run your commands against the ```./migrate.exe``` executable.  

For instance, on the command-line,  
- To view the migration status ```./migrate st --env=development --path=C:\Android\ITT\ITT-WEB\db\migrations```  
- To migrate the add the db tables ```./migrate up --env=development --path=C:\Android\ITT\ITT-WEB\db\migrations```  
- To undo a migration ```./migrate down --env=development --path=C:\Android\ITT\ITT-WEB\db\migrations```  
The ```--path``` value must point to your [```migrations```](db/migrations/) directory.

##### 2. [DATA ](data)

This gradle module provides a simple way to access our database. It uses the [Mybatis Generator](https://mybatis.org/generator/) to generate our Models and DAO classes. These models map the tables in our database.  
No additional setup is required here, as the configurations for this module are bundled in with the next module.

##### 3. [BACKEND ](backend)

This is a Spring MVC app that will run our backend.
- It is written purely in Kotlin.
- It is built in top of Spring Boot 2.0.
- It uses the [Quartz Scheduler](http://www.quartz-scheduler.org/) to run jobs.
- It contains a web Controller with a basic security setup. 
- It contains a service abstraction layer to manipulate our data.
- It can be run from the command-line via an embedded Tomcat server.

To set up,  
1. The [```application.properties```](backend/src/main/resources/application.properties) file holds all our configurations.
- The ```spring.datasource.url``` property points to our database set up in the DB setup above.
- The ```server.port``` property points to the port our server will run on, currently set as ```8081```  
The app can be launched using on the command line as a gradle task. 

To start, in the [main](backend)  directory, open the command-line and run ```gradlew bootRun```  

It can also be run simply from any java IDE via the Application class

##### [DOCKER ](backend)

A [Dockerfile](Dockerfile) has been provided for building an image of the backend. The image can be built with the following command in any environment with docker supported.  
```
docker build \       
-t myorg/itt \
--build-arg port=8081 \
--build-arg db='jdbc:mysql://localhost:25060/itt-db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EAT' \
--build-arg dbUser=root \
--build-arg dbPassword=root .
```

The build arguments are as follows:-
- ```port``` - The port to run the spring app from. You can change it from ```8081```.
- ```db``` - The url to your database. In this case it is a mysql db on ```localhost```, on port ```2560```, named ```itt-db```.
- ```dbUser``` - The database user with permission over the above database, in this case ```root```.
- ```dbPassword``` - The password of the above database user, in this case ```root```.

After the docker image has been built, it can be run with the command ```docker run -p 8081:8081 myorg/itt```
