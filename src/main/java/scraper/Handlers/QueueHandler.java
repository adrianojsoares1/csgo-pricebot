package scraper.Handlers;

import io.vertx.core.logging.Logger;
import scraper.Models.Request;

import java.util.Queue;

public class QueueHandler extends ParentHandler<Long> {

  public static final int DELAY = 2000;

  private RequestHandler requestHandler;

  public QueueHandler(Logger logger, Queue<Request> requestQueue, RequestHandler requestHandler){
    super(logger, requestQueue);
    this.requestHandler = requestHandler;
  }

  @Override
  public void handle(Long event) {
    if(this.requestQueue.isEmpty())
      return;

    Request request = requestQueue.poll();

    System.out.println(request.getSkin().getCompleteName() + ": " + request.getUrl() + '\n');
    //this.client.getAbs(request.getUrl()).as(BodyCodec.jsonObject()).send(this.requestHandler);
  }
}

