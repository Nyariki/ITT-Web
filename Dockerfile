#build arguments
ARG port=8080
ARG env=development
ARG db=itt
ARG dbUser=root
ARG dbPassword=root

## 1. Filter Stage: Will replace runtime args to placeholders in source
FROM alpine:3.7 as filter
RUN echo "Stage 1: Filter"
#Expose global args to image
ARG port
ARG env
ARG db
ARG dbUser
ARG dbPassword
#copy source
COPY . /src
#set the working directory
WORKDIR /src
#Replace all occurences of our config values with the appropriate build arguments
RUN find . -type f | xargs sed -i "s~@port@~${port}~g"
RUN find . -type f | xargs sed -i "s~@env@~${env}~g"
RUN find . -type f | xargs sed -i "s~@datasource.url@~${db}~g"
RUN find . -type f | xargs sed -i "s~@datasource.username@~${dbUser}~g"
RUN find . -type f | xargs sed -i "s~@datasource.password@~${dbPassword}~g"


## 2. Build Stage: Will build the fat jar from the copied source
FROM gradle:jdk8 as build
RUN echo "Stage 2: Build"
#copy the filtered code from the filter stage
COPY --from=filter  --chown=gradle:gradle /src/ /home/gradle/src
#set the working directory
WORKDIR /home/gradle/src/backend
#build the jar
RUN gradle build --no-daemon -x test


## 3. Run Stage: Will run the jar build from the build stage
FROM openjdk:8-jdk-alpine as run
RUN echo "Stage 3: Run"
#Expose global arg to image
ARG port
#Expose port
EXPOSE ${port}
VOLUME /tmp
#copy the built jar
COPY --from=build /home/gradle/src/backend/build/libs/*.jar itt.jar
ENTRYPOINT ["java","-jar","/itt.jar"]
