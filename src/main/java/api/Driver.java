package api;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

public class Driver {
  public static void main(String[] args){

    //Workaround for netty complaint about DNS resolver. May have unintended consequences.
    System.setProperty("vertx.disableDnsResolver", "true");

    Vertx vertx = Vertx.vertx();
    Verticle application = new MainVerticle();

    vertx.deployVerticle(application);
  }
}
