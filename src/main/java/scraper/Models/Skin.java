package scraper.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class Skin {

  @JsonProperty
  private String weapon;

  @JsonProperty
  private String name;

  @JsonProperty
  private LinkedList<String> urls;

  public String getCompleteName(){
    return weapon + " | " + name;
  }

  public String getWeapon() {
    return weapon;
  }

  public Skin setWeapon(String weapon) {
    this.weapon = weapon;
    return this;
  }

  public String getName() {
    return name;
  }

  public Skin setName(String name) {
    this.name = name;
    return this;
  }

  public LinkedList<String> getUrls() {
    return urls;
  }

  public Skin setUrls(LinkedList<String> urls) {
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
