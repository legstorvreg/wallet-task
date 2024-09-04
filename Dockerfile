FROM tomcat:9.0-jdk8

COPY target/spring-mvc-app1.war /usr/local/tomcat/webapps/

EXPOSE 8080