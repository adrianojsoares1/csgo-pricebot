package api.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class MarketResponse {

  @JsonProperty
  public String market_name;

  @JsonProperty
  public String rarity;

  @JsonProperty
  public boolean success;

  @JsonProperty
  public int start;

  @JsonProperty
  public int pagesize;

  @JsonProperty
  public int total_count;

  @JsonProperty
  public Map<Long, MarketListing> listinginfo;

  public String toString(){
    StringBuilder builder = new StringBuilder();
    builder.append("Name: ").append(market_name).append('\n');
    builder.append("Rarity: ").append(rarity).append('\n');
    builder.append("Total: ").append(total_count).append('\n');
    listinginfo.forEach((k, v) -> builder.append(v.toString()).append('\n'));

    return builder.toString();
  }

}
