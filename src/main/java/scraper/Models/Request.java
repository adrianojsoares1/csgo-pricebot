package scraper.Models;

public class Request {

  public Skin getSkin() {
    return skin;
  }

  public int getIndex() {
    return index;
  }

  public String getByIndex(){
    return skin.getUrls().get(index);
  }

  private Skin skin;
  private int index;

  public Request(Skin skin, int index){
    this.skin = skin;
    this.index = index;
  }
}
