package scraper.Handlers;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import scraper.Models.MarketResponse;
import scraper.Models.PricebotError;

public class MarketResponseHandler implements Handler<AsyncResult<HttpResponse<JsonObject>>> {

  private WebClient client;
  private PricebotResponseHandler handler;

  private Logger logs = LoggerFactory.getLogger(MarketResponseHandler.class);

  public MarketResponseHandler(WebClient client, PricebotResponseHandler responseHandler){
    this.client = client;
    this.handler = responseHandler;
  }

  @Override
  public void handle(AsyncResult<HttpResponse<JsonObject>> event) {
    if(event.failed())
      throw new PricebotError(event.cause().getMessage());

    JsonObject body = event.result().body();
    MarketResponse response = body.mapTo(MarketResponse.class);

    String API_URL = System.getenv("API_URL");

    if(API_URL == null)
      throw new PricebotError("Missing API URL, cannot proceed.");

    logs.debug("Sending Market response\n{0} to\n{1}", response.toString(), API_URL);

    client.putAbs(API_URL).sendJson(response, this.handler);
  }
}
