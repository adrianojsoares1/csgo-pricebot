package scraper;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.parsetools.JsonEventType;
import io.vertx.core.parsetools.JsonParser;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import scraper.Models.*;

public class MainVerticle extends AbstractVerticle {

  private WebClient client;

  private ThrowableHandler<AsyncResult<HttpResponse<JsonObject>>> requestHandler;
  private ThrowableHandler<AsyncResult<AsyncFile>> weaponReadHandler;
  private Handler<Long> timerHandler;

  private final Logger logs = LoggerFactory.getLogger(MainVerticle.class);

  /*ENTRY POINT*/
  @Override
  public void start(Future<Void> startFuture) throws Exception {
    this.client = WebClient.create(vertx);

    this.requestHandler = initializeRequestHandler();
    this.weaponReadHandler = initializeWeaponReadHandler();
    this.timerHandler = initializeTimerHandler();

    vertx.setTimer(1, timerHandler);
  }

  private ThrowableHandler<AsyncResult<AsyncFile>> initializeWeaponReadHandler() {
    return ar -> {
      if (ar.failed())
        throw ar.cause();

      AsyncFile weaponsFile = ar.result();
      JsonParser parser = JsonParser.newParser(weaponsFile).objectValueMode();

      parser.handler(event -> {
        if (event.type() == JsonEventType.VALUE)
          try {
            Weapon weapon = event.mapTo(Weapon.class);
            sendRequestsForWeapon(weapon);
          } catch (IllegalArgumentException e) {
            logs.error(e.getMessage());
          }
      }) //end λ

      .exceptionHandler(exc -> {
        logs.error(exc.getMessage());
        weaponsFile.close();
      }) //end λ

      .endHandler(x -> {
        logs.info("Finished parsing the json file.");
        weaponsFile.close();
      }); //end λ

    }; //end λ
  }

  private void sendRequestsForWeapon(Weapon weapon){
    weapon.getSkins().forEach(skin -> {
      skin.getUrls().forEach(url -> {

        client.getAbs(url).as(BodyCodec.jsonObject()).send(asyncHandlerEvent -> {
          try{
            requestHandler.handle(asyncHandlerEvent);
          }
          catch(Throwable t){
            logs.error(t.getMessage());
          }
        }); //end λ
      }); //end λ
    }); //end λ
  }

  private ThrowableHandler<AsyncResult<HttpResponse<JsonObject>>> initializeRequestHandler(){
    return event -> {
      if(event.failed())
        throw event.cause();

      HttpResponse<JsonObject> response = event.result();

      System.out.println(response.body().toString());
    };
  }

  private Handler<Long> initializeTimerHandler(){
    return event -> {
      OpenOptions options = new OpenOptions();
      String filepath = "src/main/java/scraper/resources/items.json"; //TODO make this an env var

      vertx.fileSystem().open(filepath, options, readEvent -> {
        try{
          weaponReadHandler.handle(readEvent);
        }
        catch(Throwable t){
          logs.error(t.getMessage());
        }
      });
    };
  }

}
