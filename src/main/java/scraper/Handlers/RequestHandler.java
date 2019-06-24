package scraper.Handlers;

import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import scraper.Models.Request;

import java.util.Queue;

public class RequestHandler extends AbstractHandler<Long> {

  public static final int DELAY = 10000; //10 seconds

  private MarketResponseHandler responseHandler;
  private WebClient webClient;

  public RequestHandler(Queue<Request> requestQueue, MarketResponseHandler requestHandler, WebClient client){
    super(LoggerFactory.getLogger(RequestHandler.class), requestQueue);
    this.responseHandler = requestHandler;
    this.webClient = client;
  }

  @Override
  public void handle(Long event) {
    if(requestQueue.isEmpty())
      return;

    Request request = requestQueue.poll();

    logs.info("Requesting {0}", request.getUrl());

    webClient.getAbs(request.getUrl()).as(BodyCodec.jsonObject()).send(responseHandler);
  }
}

