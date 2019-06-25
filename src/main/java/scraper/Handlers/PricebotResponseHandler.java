package scraper.Handlers;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.HttpResponse;

public class PricebotResponseHandler implements Handler<AsyncResult<HttpResponse<Buffer>>> {

  private Logger logs = LoggerFactory.getLogger(PricebotResponseHandler.class);

  @Override
  public void handle(AsyncResult<HttpResponse<Buffer>> event){

  }
}
