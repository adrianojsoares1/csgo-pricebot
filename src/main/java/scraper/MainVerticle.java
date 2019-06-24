package scraper;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.WebClient;
import scraper.Handlers.FileReadHandler;
import scraper.Handlers.RequestHandler;
import scraper.Handlers.ResponseHandler;
import scraper.Models.Request;

import java.util.LinkedList;
import java.util.Queue;

public class MainVerticle extends AbstractVerticle {

  private FileReadHandler weaponReadHandler;
  private RequestHandler requestHandler;
  private ResponseHandler responseHandler;

  private Queue<Request> requestQueue = new LinkedList<>();
  private final Logger logs = LoggerFactory.getLogger(MainVerticle.class);

  private Handler<Long> verticleHandler;

  @Override
  public void start(Future<Void> startFuture) {
    WebClient client = WebClient.create(vertx);

    weaponReadHandler = new FileReadHandler(this.requestQueue);
    responseHandler = new ResponseHandler();
    requestHandler = new RequestHandler(this.requestQueue, this.responseHandler, client);

    verticleHandler = event -> {
      OpenOptions options = new OpenOptions();
      String filepath = System.getenv("ITEM_FILEPATH");

      if(filepath != null)
        vertx.fileSystem().open(filepath, options, weaponReadHandler);
    };

    vertx.setTimer(1, verticleHandler);
    vertx.setPeriodic(RequestHandler.DELAY, requestHandler);
  }
}
