package scraper.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.List;

public class Skin {
  @JsonProperty
  private String name;

  @JsonProperty
  private List<String> urls;

  public String getName() {
    return name;
  }

  public Skin setName(String name) {
    this.name = name;
    return this;
  }

  public List<String> getUrls() {
    return urls;
  }

  public Skin setUrls(List<String> urls) {
    this.urls = urls;
    return this;
  }

  @Override
  public String toString(){
    StringBuilder tr = new StringBuilder("Skin Name: ").append(name).append("\n");
    urls.forEach(x -> tr.append(x).append("\n"));
    return tr.toString();
  }
}
