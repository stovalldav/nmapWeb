#!/bin/bash

IP=$(ip a show ens33 | grep "inet " |awk -F " " '{print $2}')
MASK=$(echo $IP|awk -F "/" '{print $2}')
GATEWAY=$(echo $IP|awk -F"/" '{print $1}' |awk -F "." '{print $1"."$2"."$3"."1}')
SUBNET=$(echo $IP|awk -F"/" '{print $1}' |awk -F "." '{print $1"."$2"."$3"."0}')"/"$MASK
NET=$(echo $IP|awk -F"/" '{print $1}' |awk -F "." '{print $1"."$2"."$3"."0}').123

docker network create -d macvlan --subnet $SUBNET --gateway $GATEWAY -o parent=ens33 nmnet

docker run -d -p 8080:8080 --name nmapWeb --net nmnet --ip $NET -v $(pwd)/target/nmapJDBCWEb.war:/usr/local/tomcat/webapps/nmapJDBCWeb.war -v $(pwd)/target/nmapJDBCWeb:/usr/local/tomcat/webapps/ROOT nmcat

docker network connect dbnet nmcat