package scraper;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.WebClient;
import scraper.Handlers.FileReadHandler;
import scraper.Handlers.QueueHandler;
import scraper.Handlers.RequestHandler;
import scraper.Models.Request;

import java.util.LinkedList;
import java.util.Queue;

public class MainVerticle extends AbstractVerticle {

  private FileReadHandler weaponReadHandler;
  private RequestHandler requestHandler;
  private QueueHandler queueHandler;

  private Queue<Request> requestQueue = new LinkedList<>();
  private final Logger logs = LoggerFactory.getLogger(MainVerticle.class);

  private Handler<Long> verticleHandler;

  @Override
  public void start(Future<Void> startFuture) {
    WebClient client = WebClient.create(vertx);

    weaponReadHandler = new FileReadHandler(this.logs, this.requestQueue);
    requestHandler = new RequestHandler(this.logs, client);
    queueHandler = new QueueHandler(this.logs, this.requestQueue, this.requestHandler);
    verticleHandler = createVerticleHandler();

    vertx.setTimer(1, verticleHandler);
    vertx.setPeriodic(QueueHandler.DELAY, queueHandler);
  }

  private Handler<Long> createVerticleHandler(){
    return event -> {
      OpenOptions options = new OpenOptions();
      String filepath = System.getenv("ITEM_FILEPATH");

      if(filepath != null)
        vertx.fileSystem().open(filepath, options, weaponReadHandler);
    };
  }
}
