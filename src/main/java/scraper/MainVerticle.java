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
import io.vertx.ext.web.codec.BodyCodec;

import java.util.Iterator;
import java.util.Map;

public class MainVerticle extends AbstractVerticle {

  private WebClient client;

  private ThrowableHandler<AsyncResult<HttpResponse<JsonObject>>> requestHandler;
  private ThrowableHandler<AsyncResult<AsyncFile>> weaponReadHandler;
  private Handler<Long> timerHandler;

  private final Logger logs = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    this.client = WebClient.create(vertx);

    this.timerHandler = initializeTimerHandler();
    this.requestHandler = initializeRequestHandler();
    this.weaponReadHandler = initializeWeaponReadHandler();

    Handler<AsyncResult<AsyncFile>> throwableHandler = getThrowableHandler(weaponReadHandler, logs);
    vertx.fileSystem().open("src/main/java/scraper/resources/items.json", new OpenOptions(), throwableHandler);

    //vertx.setPeriodic(5000, timerHandler);
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
          JsonObject json = event.objectValue();
          logs.info("Requesting info for weapon {}", json.getString("name"));

          Iterator<Map.Entry<String, Object>> skins = json.getJsonObject("skins").iterator();
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


    };
  }

  private void request(String uri){
    String url = Utilities.buildURL(uri);

    this.client.getAbs(url)
      .as(BodyCodec.jsonObject())
      .send(this.requestHandler);
  }

  private static <T> Handler<T> getThrowableHandler(ThrowableHandler<T> handler, Logger logs) {
    return event -> {
      try { handler.handle(event); }
      catch(Throwable e){
        logs.error(e.getMessage());
      }
    };
  }

}
