# VertxClusterDemo
Demo Vertx Cluster ready for execution on Kubernetes environment composed by two distinct Gradle Projects:

- 1 Sender Verticle, to send messages over a clusterized EventBus
- 1 Receiver Verticle, to receive messages sent over a clusterized EventBus

Both projects contain a DockerFile and a deploy.yaml for easy integration as kubernetes deployments.

These two projects, form a two-node fully modular Vertx Cluster.



### Pre-requisites
- Docker - For creation of the docker images for deployment on kubernetes
- Minikube (for local execution) - For execution
- Kubectl - For deployment of docker images on kubernetes.


### Build and Execution

##### 1- Build projects
For each project, from root dir for the project execute:
```gradle build```
##### 2- Create Docker Images

Set Docker to use the Minikube's own Docker daemon, from terminal execute:
```eval $(minikube docker-env)```

For each project, execute from root:
```docker build -t vertx-cluster-<name> -f Dockerfile .```
substitute <name> for ```receiver``` or ```sender``` accordingly.

##### 3- Deploy to Minikube 
Launch Minikube by, from terminal, executing:
```minikube start```

Deploy the create images to the kubernetes environment
```kubectl run <name>-verticle --image=vertx-cluster-<name> --image-pull-policy=Never```

substitute <name> for ```receiver``` or ```sender``` accordingly. 

##### 4- Verify Logs

Open the Minikube Dashboard by, from terminal, executing:
```minikube dashboard```

Go to **Pods->Logs** and verify the logs for both deployments.

Logs for Sender Deployment should look like this:
```
20:23:25.265 [vert.x-eventloop-thread-0] INFO  SenderVerticle - Spitting messages
20:23:26.265 [vert.x-eventloop-thread-0] INFO  SenderVerticle - Spitting messages
20:23:27.265 [vert.x-eventloop-thread-0] INFO  SenderVerticle - Spitting messages
20:23:28.265 [vert.x-eventloop-thread-0] INFO  SenderVerticle - Spitting messages
```
Logs for Receiver Deployment should look like this:

```
20:25:05.266 [vert.x-eventloop-thread-1] DEBUG ReceiverVerticle - Received message: TEST MESSAGE 
20:25:06.266 [vert.x-eventloop-thread-1] DEBUG ReceiverVerticle - Received message: TEST MESSAGE 
20:25:07.266 [vert.x-eventloop-thread-1] DEBUG ReceiverVerticle - Received message: TEST MESSAGE 
20:25:08.267 [vert.x-eventloop-thread-1] DEBUG ReceiverVerticle - Received message: TEST MESSAGE 
```

### Final Note
Weirdly, none of the configurations documented on Vertx's website was necessary.
Just executing the Verticles in cluster mode with the default cluster manager configuration worked just fine.







