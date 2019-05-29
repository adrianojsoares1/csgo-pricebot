package scraper;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

public class Driver {
  public static void main(String[] args){
    Vertx vertx = Vertx.vertx();
    Verticle application = new MainVerticle();

    vertx.deployVerticle(application);
  }
}
