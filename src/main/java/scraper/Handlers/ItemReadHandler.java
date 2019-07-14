package scraper.Handlers;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.parsetools.JsonEventType;
import io.vertx.core.parsetools.JsonParser;
import scraper.Models.Request;
import scraper.Models.Skin;
import scraper.Models.PricebotError;

import java.util.Queue;

public class ItemReadHandler implements Handler<AsyncResult<AsyncFile>> {

  private Logger logs = LoggerFactory.getLogger(ItemReadHandler.class);

  private Queue<Request> requestQueue;

  public ItemReadHandler(Queue<Request> requestQueue){
    this.requestQueue = requestQueue;
  }

  @Override
  public void handle(AsyncResult<AsyncFile> ar) {
    if (ar.failed()){
      throw new PricebotError(ar.cause().getMessage());
    }

    AsyncFile weaponsFile = ar.result();
    JsonParser parser = JsonParser.newParser(weaponsFile).objectValueMode();

    parser.handler(event -> {
      if (event.type() == JsonEventType.VALUE) {
        try {
          Skin skin = event.mapTo(Skin.class);

          int size = skin.getUrls().size();
          for(int i = 0; i < size; i++)
            requestQueue.add(new Request(skin, i));

        } catch (IllegalArgumentException e) {
          logs.error(e.getMessage());
        }
      }
    }) //end λ

      .exceptionHandler(exc -> {
        logs.error(exc.getMessage());
        weaponsFile.close();
      }) //end λ

      .endHandler(x -> {
        logs.info("Finished parsing the json file. Executing Queries...");
        weaponsFile.close();
      }); //end λ
  }
}
