package scraper.Handlers;

import io.vertx.core.AsyncResult;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.HttpResponse;
import scraper.Models.MarketResponse;

public class ResponseHandler extends ParentHandler<AsyncResult<HttpResponse<JsonObject>>>{

  public ResponseHandler(){
    super(LoggerFactory.getLogger(ResponseHandler.class), null);
  }

  @Override
  public void handle(AsyncResult<HttpResponse<JsonObject>> event) {
    MarketResponse response = event.result().body().mapTo(MarketResponse.class);

    logs.info(response.toString());
  }
}
