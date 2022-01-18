package ibf.ssf.ssfa.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf.ssf.ssfa.models.BookModel;
import ibf.ssf.ssfa.models.URLModel;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import static ibf.ssf.ssfa.Constants.*;

@Service
@Primary
public class BookServiceImple implements BookService {

  private final Logger logger = Logger.getLogger(BookService.class.getName());

  public List<URLModel> search(String title) {
    title = title.replace(" ", "+");
    String url = UriComponentsBuilder.fromUriString(SEARCH_URL)
        .queryParam("title", title)
        .queryParam("limit", SEARCH_LIMIT_PARAM)
        .queryParam("fields", SEARCH_FIELD_PARAM)
        .toUriString();

    logger.log(Level.INFO, url);

    // construct request entity
    final RequestEntity<Void> req = RequestEntity.get(url).build();
    // initiate rest template for querying
    final RestTemplate template = new RestTemplate();
    // obtain response entity
    final ResponseEntity<String> resp = template.exchange(req, String.class);

    logger.info("Status code: " + resp.getStatusCode());

    if (resp.getStatusCode() != HttpStatus.OK)
      throw new IllegalArgumentException(
          "Error: status code %s".formatted(resp.getStatusCode().toString()));
    // get JSONstring from response
    final String body = resp.getBody();

    logger.log(Level.INFO, "payload: %s".formatted(body));
    try (InputStream is = new ByteArrayInputStream(body.getBytes())) {
      final JsonReader reader = Json.createReader(is);
      final JsonObject result = reader.readObject();
      // get weather array
      final JsonArray readings = result.getJsonArray("docs");
      return readings.stream()
          .map(v -> (JsonObject) v)
          .map(URLModel::create)
          .collect(Collectors.toList());
    } catch (Exception e) {

    }
    return Collections.emptyList();

  }

  public BookModel getBookByKey(String bookKey) {
    String url = WORKS_URL + bookKey + ".json";
    logger.log(Level.INFO, url);

    // construct request entity
    final RequestEntity<Void> req = RequestEntity.get(url).build();
    // initiate rest template for querying
    final RestTemplate template = new RestTemplate();
    // obtain response entity
    final ResponseEntity<String> resp = template.exchange(req, String.class);

    logger.info("Status code: " + resp.getStatusCode());

    if (resp.getStatusCode() != HttpStatus.OK)
      throw new IllegalArgumentException(
          "Error: status code %s".formatted(resp.getStatusCode().toString()));
    // get JSONstring from response
    final String body = resp.getBody();

    logger.log(Level.INFO, "payload: %s".formatted(body));
    try (InputStream is = new ByteArrayInputStream(body.getBytes())) {

      final JsonReader reader = Json.createReader(is);
      final JsonObject result = reader.readObject();
      BookModel book = BookModel.create(result);
      logger.log(Level.INFO, "Title: %s".formatted(book.getTitle()));
      logger.log(Level.INFO, "Description: %s".formatted(book.getDescription()));
      logger.log(Level.INFO, "Excerpt: %s".formatted(book.getExcerpt()));
      logger.log(Level.INFO, "Cover: %s".formatted(book.getCover()));
      return book;
    } catch (Exception e) {

    }
    return new BookModel();

  }

}
