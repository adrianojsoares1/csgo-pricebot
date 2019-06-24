package scraper.Handlers;

import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import scraper.Models.Request;

import java.util.Queue;

public abstract class ParentHandler<T> implements Handler<T> {
  Logger logs;
  Queue<Request> requestQueue;

  ParentHandler(Logger logs, Queue<Request> requestQueue){
    this.logs = logs;
    this.requestQueue = requestQueue;
  }

  public ParentHandler setLogs(Logger l){
    this.logs = l;
    return this;
  }

  public ParentHandler setQueue(Queue<Request> requestQueue){
    this.requestQueue = requestQueue;
    return this;
  }

  @Override
  public abstract void handle(T event);

}
