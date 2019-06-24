package scraper.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(value = {"results_html", "assets", "currency", "hovers", "app_data"})
public class MarketResponse {
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
    builder.append("\nSuccess: ").append(success);
    builder.append("\nListings: ").append(total_count);

    for(Long key : listinginfo.keySet())
      builder.append(listinginfo.get(key));

    return builder.toString();
  }
}
