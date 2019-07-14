package api.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

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

  public int intPrice(){
    return converted_price + converted_fee;
  }

  public double priceUSD(){
    return intPrice() / 100.0;
  }

  public String toString(){
    return "ID: " + listingid + " | Price: " + priceUSD();
  }

}
