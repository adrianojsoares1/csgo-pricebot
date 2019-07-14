package api;

import api.Handlers.ApiRequestHandler;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;


public class MainVerticle extends AbstractVerticle {
  private RedisClient client;
  private StatefulRedisConnection<String, String> connection;
  private RedisAsyncCommands<String, String> commands;

  private HttpServer server;
  private ApiRequestHandler requestHandler;

  private Logger logs = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Future<Void> startFuture) {
    startRedis();
    startServer();
  }

  public void startServer(){
    server = vertx.createHttpServer();
    requestHandler = new ApiRequestHandler(commands);

    server.connectionHandler(event -> logs.info("New connection opened from {0}", event.remoteAddress()));
    server.requestHandler(requestHandler);

    server.listen(9000);
  }

  public void startRedis(){
    client = RedisClient.create("redis://127.0.0.1:6379");
    connection = client.connect();
    commands = connection.async();

    commands.ping().thenAcceptAsync(x -> logs.info("Redis is online."));
  }
}
