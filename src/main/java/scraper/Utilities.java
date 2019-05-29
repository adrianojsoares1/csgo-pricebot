package scraper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class Utilities {
  private static final String STEAM_COMMUNITY_URL = "https://steamcommunity.com/market/listings/730/";
  private static final String STEAM_COMMUNITY_QUERY = "/render?start=0&count=10&currency=3&language=english&format=json";

  private Utilities(){}

  public static String URLEncode(String item){
    return URLEncoder.encode(item, StandardCharsets.UTF_8).replace("+", "%20");
  }

  public static String buildURL(String weapon){
    return new StringBuilder()
      .append(STEAM_COMMUNITY_URL)
      .append(URLEncode(weapon))
      .append(STEAM_COMMUNITY_QUERY)
      .toString();
  }




}
