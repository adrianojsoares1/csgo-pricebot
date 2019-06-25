package scraper.Handlers;

import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import scraper.Models.Request;

import java.util.Queue;

public class RequestHandler implements Handler<Long> {

  public static final int DELAY = 10000; //10 seconds

  private Logger logs = LoggerFactory.getLogger(RequestHandler.class);

  private MarketResponseHandler responseHandler;
  private WebClient webClient;
  private Queue<Request> requestQueue;

  public RequestHandler(Queue<Request> requestQueue, MarketResponseHandler requestHandler, WebClient client){
    this.responseHandler = requestHandler;
    this.webClient = client;
    this.requestQueue = requestQueue;
  }

  @Override
  public void handle(Long event) {
    if(requestQueue.isEmpty())
      return;

    Request request = requestQueue.poll();

    String url = request.getByIndex();

    logs.debug("Retrieving {0}", url);

    webClient.getAbs(url).as(BodyCodec.jsonObject()).send(responseHandler);
  }
}

