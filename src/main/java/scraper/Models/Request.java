package scraper.Models;

public class Request {
  public Skin getSkin() {
    return skin;
  }

  public Request setSkin(Skin weapon) {
    this.skin = weapon;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public Request setUrl(String url) {
    this.url = url;
    return this;
  }

  private Skin skin;
  private String url;
}
