FROM openjdk:11 as build
MAINTAINER huhan<191830052@smail.nju.edu.cn>
ADD target/demo001-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/demo.jar"]
