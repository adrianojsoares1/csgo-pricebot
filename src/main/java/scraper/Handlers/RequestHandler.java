package scraper.Handlers;

import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import scraper.Models.Request;

import java.util.Queue;

public class RequestHandler extends ParentHandler<Long> {

  public static final int DELAY = 10000; //10 seconds

  private ResponseHandler responseHandler;
  private WebClient webClient;

  public RequestHandler(Queue<Request> requestQueue, ResponseHandler requestHandler, WebClient client){
    super(LoggerFactory.getLogger(RequestHandler.class), requestQueue);
    this.responseHandler = requestHandler;
    this.webClient = client;
  }

  @Override
  public void handle(Long event) {
    if(this.requestQueue.isEmpty())
      return;

    Request request = requestQueue.poll();

    logs.info("Requesting {0}", request.getUrl());
    this.webClient.getAbs(request.getUrl()).as(BodyCodec.jsonObject()).send(this.responseHandler);
  }
}

