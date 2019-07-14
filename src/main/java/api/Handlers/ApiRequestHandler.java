package api.Handlers;

import io.lettuce.core.api.async.RedisAsyncCommands;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import scraper.Models.MarketResponse;

public class ApiRequestHandler implements Handler<HttpServerRequest> {

  private RedisAsyncCommands<String, String> commands;

  private Logger logs = LoggerFactory.getLogger(ApiRequestHandler.class);

  private final Buffer NO_PARAM_ERROR = Buffer.buffer("Missing necessary query parameter 'name'.");
  private final Buffer INSERTION_FAILED_ERROR = Buffer.buffer("Item could not be inserted into database.");
  private final Buffer NOT_FOUND_ERROR = Buffer.buffer("Item was not found in the database.");
  private final Buffer BAD_JSON_ERROR = Buffer.buffer("Unable to parse JSON, cannot append to database. The body of the request may be malformed.");

  private ApiRequestHandler(){}
  public ApiRequestHandler(RedisAsyncCommands<String, String> commands){ this.commands = commands; }

  @Override
  public void handle(HttpServerRequest request) {
    HttpServerResponse response = request.response();

    switch(request.method()){
      case PUT: put(request, response); break;
      case GET: get(request, response); break;
      default: response.setStatusCode(400).end("Invalid HTTP Method!");
    }
  }

  private void put(HttpServerRequest request, HttpServerResponse response){
    Buffer body = Buffer.buffer();

    request.handler(body::appendBuffer);

    request.endHandler(nothing -> {

      MarketResponse payload;
      try {
        payload = Json.decodeValue(body, MarketResponse.class);
      }
      catch(DecodeException de){
        logs.error(BAD_JSON_ERROR + de.getMessage());
        response.setStatusCode(400).end(BAD_JSON_ERROR);
        return;
      }

      final String key = request.getParam("name");
      if(key == null){
        response.setStatusCode(400).end(NO_PARAM_ERROR);
        return;
      }

      commands.set(key, Json.encode(payload))
        .thenAcceptAsync(z -> {
          if (z.equals("OK")) {
            response.setStatusCode(202).end();
            logs.info("Weapon {0} inserted to database.", key);
          }
          else response.setStatusCode(500).end(INSERTION_FAILED_ERROR.appendString("\nRedis Error: " + z));
        });
    });
  }

  private void get(HttpServerRequest request, HttpServerResponse response){
    final String key = request.getParam("name");

    if(key == null){
      response.setStatusCode(400).end(NO_PARAM_ERROR);
    }
    else commands.get(key)
      .thenAcceptAsync(data -> {
        if(data == null)
          response.setStatusCode(404).end(NOT_FOUND_ERROR);
        else response.end(data);
      });
  }

} //end class
