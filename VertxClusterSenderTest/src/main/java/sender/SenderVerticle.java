package sender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class SenderVerticle extends AbstractVerticle
{
	Vertx vertx;
	public void start(Future<Void> fut)
	{
		Logger logger = LogManager.getLogger("SenderVerticle");


		ClusterManager clusterManager = new HazelcastClusterManager();
		VertxOptions vertxOptions = new VertxOptions()
				.setClustered(true)
				.setClusterManager(clusterManager)
				.setEventBusOptions(new EventBusOptions()
					.setClustered(true)
					.setClusterPublicHost(System.getenv("POD_IP"))
					.setClusterPublicPort(5702));

		Vertx.clusteredVertx(vertxOptions, res -> {
			if (res.succeeded()) {
				vertx = res.result();
				final EventBus eventBus = vertx.eventBus();

				logger.info("Current Thread Id {} Is Clustered {} ", Thread.currentThread().getId(), vertx.isClustered());

				vertx.setPeriodic(1000, itsTime -> 
				{
					eventBus.publish("TEST_ADDRESS", "TEST MESSAGE");
					logger.info("Spitting messages");
				});

				fut.complete();
			} 
			else 
			{
				logger.error("Shit failed yo");
			}
		});
	}
}