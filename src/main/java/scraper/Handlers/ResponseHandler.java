package scraper.Handlers;

import io.vertx.core.AsyncResult;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.HttpResponse;

public class ResponseHandler extends ParentHandler<AsyncResult<HttpResponse<JsonObject>>>{

  public ResponseHandler(){
    super(LoggerFactory.getLogger(ResponseHandler.class), null);
  }

  @Override
  public void handle(AsyncResult<HttpResponse<JsonObject>> event) {
    JsonObject response = event.result().body();

    logs.info(response.toString());
  }
}
