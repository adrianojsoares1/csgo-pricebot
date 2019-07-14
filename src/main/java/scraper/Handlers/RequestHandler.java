package scraper.Handlers;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import scraper.Models.MarketResponse;
import scraper.Models.PricebotError;
import scraper.Models.Request;

import java.util.Queue;

public class RequestHandler implements Handler<Long> {

  public static final int DELAY = 10000; //10 seconds

  private Logger logs = LoggerFactory.getLogger(RequestHandler.class);

  private WebClient webClient;
  private Queue<Request> requestQueue;

  public RequestHandler(Queue<Request> requestQueue, WebClient client){
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

  private Handler<AsyncResult<HttpResponse<JsonObject>>> responseHandler = event -> {
    if(event.failed())
      throw new PricebotError(event.cause().getMessage());

    JsonObject body = event.result().body();
    MarketResponse response = body.mapTo(MarketResponse.class);

    String API_URL = System.getenv("API_URL");

    if(API_URL == null)
      throw new PricebotError("Missing API URL, cannot proceed.");

    logs.info("Sending Market response to\n{0}", API_URL);

    webClient.putAbs(API_URL).sendJson(response, logs::info);
  };
}

