package scraper.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Skin {

  @JsonProperty
  private String weapon;

  @JsonProperty
  private String name;

  @JsonProperty
  private ArrayList<String> urls;

  public ArrayList<String> getUrls() {
    return urls;
  }

  @Override
  public String toString(){
    StringBuilder tr = new StringBuilder("Skin Name: ").append(name).append("\n");
    urls.forEach(x -> tr.append(x).append("\n"));
    return tr.toString();
  }
}
