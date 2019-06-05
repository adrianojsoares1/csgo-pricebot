package scraper.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Weapon {

  @JsonProperty
  private String name;

  @JsonProperty
  private String type;

  @JsonProperty
  private List<Skin> skins;

  public String getName() {
    return name;
  }

  public Weapon setName(String name) {
    this.name = name;
    return this;
  }

  public String getType() {
    return type;
  }

  public Weapon setType(String type) {
    this.type = type;
    return this;
  }

  public List<Skin> getSkins() {
    return skins;
  }

  public Weapon setSkins(List<Skin> skins) {
    this.skins = skins;
    return this;
  }

  @Override
  public String toString(){
    StringBuilder tr = new StringBuilder("Name: ").append(name).append("\nType: ").append(type).append("\n");
    skins.forEach(tr::append);
    return tr.toString();
  }

}
