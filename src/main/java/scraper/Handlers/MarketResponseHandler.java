package scraper.Handlers;

import io.vertx.core.AsyncResult;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.HttpResponse;
import scraper.Models.MarketResponse;
import scraper.Models.PricebotError;

public class MarketResponseHandler extends AbstractHandler<AsyncResult<HttpResponse<JsonObject>>> {

  public MarketResponseHandler(){
    super(LoggerFactory.getLogger(MarketResponseHandler.class), null);
  }

  @Override
  public void handle(AsyncResult<HttpResponse<JsonObject>> event) {
    if(event.failed())
      throw new PricebotError(event.cause().getMessage());

    JsonObject body = event.result().body();
    MarketResponse response = body.mapTo(MarketResponse.class);


    logs.info(Json.encodePrettily(response));
  }
}
