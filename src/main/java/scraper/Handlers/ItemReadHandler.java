package scraper.Handlers;

import io.vertx.core.AsyncResult;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.parsetools.JsonEventType;
import io.vertx.core.parsetools.JsonParser;
import scraper.Models.Request;
import scraper.Models.Skin;
import scraper.Models.PricebotError;

import java.util.Queue;

public class ItemReadHandler extends AbstractHandler<AsyncResult<AsyncFile>> {

  public ItemReadHandler(Queue<Request> requestQueue){
    super(LoggerFactory.getLogger(ItemReadHandler.class), requestQueue);
  }

  @Override
  public void handle(AsyncResult<AsyncFile> ar) {
    if (ar.failed())
      throw new PricebotError(ar.cause().getLocalizedMessage());

    AsyncFile weaponsFile = ar.result();
    JsonParser parser = JsonParser.newParser(weaponsFile).objectValueMode();

    parser.handler(event -> {
      if (event.type() == JsonEventType.VALUE)
        try {
          Skin skin = event.mapTo(Skin.class);
          skin.getUrls().forEach(url -> requestQueue.add(new Request().setSkin(skin).setUrl(url)));

        } catch (IllegalArgumentException e) {
          logs.error(e.getMessage());
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
