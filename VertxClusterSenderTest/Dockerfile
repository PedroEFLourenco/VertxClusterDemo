FROM mycujoo/java-docker

RUN ["mkdir", "deployment"]
RUN ["chmod", "a+rwx", "deployment"]
COPY build/libs/vertx-cluster-sender-1.0-SNAPSHOT-fat.jar deployment
EXPOSE 5702
CMD export POD_IP=`ifconfig eth0 | grep inet | awk '{print $2}' | cut -f2 -d":"` && java -jar deployment/vertx-cluster-sender-1.0-SNAPSHOT-fat.jar