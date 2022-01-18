package ibf.ssf.ssfa.models;

import jakarta.json.JsonObject;

public class BookModel {
  private String title;
  private String description;
  private String excerpt;
  private boolean cached = false;

  public BookModel() {

  }

  public BookModel(String[] parameters) {
    this.title = parameters[0];
    this.description = parameters[1];
    this.excerpt = parameters[2];
    this.cached = true;
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

  @Override
  public String toString() {
    return String.join("|", this.title, this.description, this.excerpt);
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

    book.setDescription(desc);

    if (o.containsKey("excerpts")) {
      book.setExcerpt(o.getJsonArray("excerpts").getJsonObject(0).getString("excerpt"));
    } else {
      book.setExcerpt("No excerpt found");
    }

    return book;
  }

}
