package scraper;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.parsetools.JsonEventType;
import io.vertx.core.parsetools.JsonParser;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import scraper.Models.*;

public class MainVerticle extends AbstractVerticle {

  private WebClient client;

  private ThrowableHandler<AsyncResult<HttpResponse<JsonObject>>> requestHandler;
  private ThrowableHandler<AsyncResult<AsyncFile>> weaponReadHandler;
  private Handler<Long> timerHandler;

  private final Logger logs = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    this.client = WebClient.create(vertx);

    this.requestHandler = initializeRequestHandler();
    this.weaponReadHandler = initializeWeaponReadHandler();
    this.timerHandler = initializeTimerHandler();
    vertx.setPeriodic(5000, timerHandler);
  }

  private ThrowableHandler<AsyncResult<AsyncFile>> initializeWeaponReadHandler(){
    return ar -> {
      if(ar.failed())
        throw ar.cause();

      AsyncFile weaponsFile = ar.result();
      JsonParser parser = JsonParser.newParser(weaponsFile).objectValueMode();

      //Handle a new JSON object in the array
      parser.handler(event -> {
        if(event.type() == JsonEventType.VALUE){
          Weapon weapon;
          try {
            weapon = event.mapTo(Weapon.class);
          }
          catch(IllegalArgumentException e){
            logs.error(e.getLocalizedMessage());
            weaponsFile.close();
            return;
          }

          System.out.println(weapon.toString());
        }
      });
      //Handle an error reading the file
      parser.exceptionHandler(exc -> {
        logs.error(exc.getMessage());
        weaponsFile.close();
      });
      //Handle the parser finishing the file
      parser.endHandler(x -> {
        logs.info("Finished parsing the json file.");
        weaponsFile.close();
      });
    };
  }

  private ThrowableHandler<AsyncResult<HttpResponse<JsonObject>>> initializeRequestHandler(){
    return event -> {



    };
  }

  private Handler<Long> initializeTimerHandler(){
    return event -> {
      OpenOptions options = new OpenOptions();
      String filepath = "src/main/java/scraper/resources/items.json";

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
