package scraper.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(value = {"results_html", "currency", "hovers", "app_data"})
public class MarketResponse {

  public String market_name;
  public String rarity;

  @JsonProperty("assets")
  private void getMarketName(Map<String, Map<String, Map<String, Map<String, Object>>>> assets){
    Map<String, Object> meta = assets.get("730").get("2").entrySet().iterator().next().getValue();

    this.market_name = (String) meta.get("market_name");
    this.rarity = (String) meta.get("type");
  }

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

  @Override
  public String toString(){
    StringBuilder builder = new StringBuilder();
    builder.append("Item name as defined: ").append(market_name);
    builder.append("\nRarity: ").append(this.rarity);
    builder.append("\nSuccess: ").append(success);
    builder.append("\nListings: ").append(total_count);

    for(Long key : listinginfo.keySet())
      builder.append(listinginfo.get(key));

    return builder.toString();
  }
}
