package ibf.ssf.ssfa.models;

import jakarta.json.JsonObject;

public class BookModel {
  private String title;
  private String description;
  private String excerpt;
  private boolean cached = false;
  private String cover;

  public BookModel() {

  }

  public BookModel(String[] parameters) {
    this.title = parameters[0];
    this.description = parameters[1];
    this.excerpt = parameters[2];
    this.cached = true;
    this.cover = parameters[3];
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getExcerpt() {
    return this.excerpt;
  }

  public void setExcerpt(String excerpt) {
    this.excerpt = excerpt;
  }

  public boolean getCached() {
    return this.cached;
  }

  public void setCached(boolean cached) {
    this.cached = cached;
  }

  public String getCover() {
    return this.cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  @Override
  public String toString() {
    return String.join("|", this.title, this.description, this.excerpt, this.cover);
  }

  public static BookModel create(JsonObject o) {
    BookModel book = new BookModel();
    book.setTitle(o.getString("title"));
    String desc = "";
    if (o.containsKey("description")) {

      try {
        desc = o.getString("description");
      } catch (ClassCastException e) {
        if (o.getJsonObject("description").containsKey("value")) {
          JsonObject descObj = o.getJsonObject("description");
          desc = descObj.getString("value");
        }
      }
    }

    if (desc == "") {
      book.setDescription("No description found.");
    } else {
      book.setDescription(desc);
    }

    if (o.containsKey("excerpts")) {
      book.setExcerpt(o.getJsonArray("excerpts").getJsonObject(0).getString("excerpt"));
    } else {
      book.setExcerpt("No excerpt found.");
    }

    if (o.containsKey("covers")) {
      book.setCover(o.getJsonArray("covers").get(0).toString());
    } else {
      book.setCover("NOT_FOUND");
    }

    return book;
  }

}
