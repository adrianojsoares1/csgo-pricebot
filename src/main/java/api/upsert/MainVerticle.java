package api.upsert;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.WebClient;
import scraper.Handlers.ItemReadHandler;
import scraper.Handlers.PricebotResponseHandler;
import scraper.Handlers.RequestHandler;
import scraper.Handlers.MarketResponseHandler;
import scraper.Models.Request;

import java.util.LinkedList;
import java.util.Queue;

public class MainVerticle extends AbstractVerticle {


  @Override
  public void start(Future<Void> startFuture) {

  }
}
