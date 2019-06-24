package scraper.Handlers;

import io.vertx.core.AsyncResult;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

public class RequestHandler extends ParentHandler<AsyncResult<HttpResponse<JsonObject>>>{

  private WebClient client;

  public RequestHandler(Logger logs, WebClient client){
    super(logs, null);

    this.client = client;
  }

  @Override
  public void handle(AsyncResult<HttpResponse<JsonObject>> event) {

  }
}
