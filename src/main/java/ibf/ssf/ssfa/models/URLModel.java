package ibf.ssf.ssfa.models;

import jakarta.json.JsonObject;

public class URLModel {

  private String key;
  private String title;
  private String link;

  public String getKey() {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public static URLModel create(JsonObject o) {
    final URLModel u = new URLModel();
    u.setKey(o.getString("key").replace("/work/", ""));
    u.setTitle(o.getString("title"));
    return u;
  }
}
