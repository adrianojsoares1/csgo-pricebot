package scraper;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.WebClient;
import scraper.Handlers.ItemReadHandler;
import scraper.Handlers.RequestHandler;
import scraper.Handlers.MarketResponseHandler;
import scraper.Models.Request;

import java.util.LinkedList;
import java.util.Queue;

public class MainVerticle extends AbstractVerticle {

  private ItemReadHandler weaponReadHandler;
  private RequestHandler requestHandler;
  private MarketResponseHandler responseHandler;

  private Queue<Request> requestQueue = new LinkedList<>();
  private final Logger logs = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Future<Void> startFuture) {
    WebClient client = WebClient.create(vertx);

    weaponReadHandler = new ItemReadHandler(this.requestQueue);
    responseHandler = new MarketResponseHandler(client);
    requestHandler = new RequestHandler(this.requestQueue, this.responseHandler, client);

    OpenOptions options = new OpenOptions();
    String filepath = System.getenv("ITEM_FILEPATH");

    if(filepath != null){
      logs.debug("Opening weapons file...");
      vertx.fileSystem().open(filepath, options, weaponReadHandler);
    }

    vertx.setPeriodic(RequestHandler.DELAY, requestHandler);
  }
}
