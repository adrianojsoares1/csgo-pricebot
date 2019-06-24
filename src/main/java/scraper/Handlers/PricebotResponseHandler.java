package scraper.Handlers;

import io.vertx.core.AsyncResult;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.HttpResponse;

public class PricebotResponseHandler extends AbstractHandler<AsyncResult<HttpResponse<JsonObject>>> {

  public PricebotResponseHandler(){
    super(LoggerFactory.getLogger(PricebotResponseHandler.class), null);
  }

  @Override
  public void handle(AsyncResult<HttpResponse<JsonObject>> event){

  }
}
