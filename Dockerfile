FROM tomcat
MAINTAINER Davis Stovall


RUN apt-get update
RUN apt-get install -y nmap
