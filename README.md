#### ITT WEB

This is the Web backend for the [ITT-Project](https://github.com/users/Nyariki/projects/2). The Web platform is written in Spring. It is divided into 3 configurable modules.

##### 1. [DB](db)

The DB modules uses [Mybatis Migrations](https://mybatis.org/migrations/) to manage a MYSQL database. The migrations are run via mysql scripts in the [scripts](ITT-WEB/db/migrations/scripts).

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
Be sure to run  ```gradlew clean build``` before the first run

It can also be run simply from any java IDE via the Application class