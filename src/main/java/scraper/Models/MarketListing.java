package scraper.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(value = {"asset", "publisher_fee_app", "publisher_fee_percent", "converted_currencyid",
    "currencyid", "price", "fee", "steam_fee", "publisher_fee"})

public class MarketListing {
  @JsonProperty
  public Long listingid;

  @JsonProperty
  public Integer converted_price;

  @JsonProperty
  public Integer converted_fee;

  @JsonProperty
  public Integer converted_steam_fee;

  @JsonProperty
  public Integer converted_publisher_fee;

  @JsonProperty
  public Integer converted_price_per_unit;

  @JsonProperty
  public Integer converted_fee_per_unit;

  @JsonProperty
  public Integer converted_steam_fee_per_unit;

  @JsonProperty
  public Integer converted_publisher_fee_per_unit;

  public int price(){
    return converted_price + converted_fee;
  }

  @Override
  public String toString(){
    return "\nListing ID: " + listingid + "\n" +
      "Price: " + converted_price + "\n" +
      "Fee: " + converted_fee + "\n";
  }
}
