
export POD_IP=`ifconfig eth0 | grep inet | awk '{print $2}' | cut -d/ -f1`
java -Dvertx.hazelcast.config=cluster.xml -jar vertx-cluster-receiver-1.0-SNAPSHOT-fat.jar -cluster -cluster-host $POD_IP -cluster-port 5702