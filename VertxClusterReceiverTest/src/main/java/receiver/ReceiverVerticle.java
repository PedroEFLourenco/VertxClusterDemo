package receiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;

public class ReceiverVerticle extends AbstractVerticle {


	public void start(Future<Void> fut)
	{
		Logger logger = LogManager.getLogger("ReceiverVerticle");


		final EventBus eventBus = vertx.eventBus();
		logger.info("Current Thread Id {} Is Clustered {} ", Thread.currentThread().getId(), vertx.isClustered());



		eventBus.consumer("TEST_ADDRESS", receivedMessage -> {
			logger.debug("Received message: {} ", receivedMessage.body().toString());
		});

		fut.complete();

	}
}