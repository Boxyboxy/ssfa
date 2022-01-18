package ibf.ssf.ssfa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ibf.ssf.ssfa.service.BookService;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ibf.ssf.ssfa.models.URLModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@Controller
@RequestMapping(path = "/search", produces = MediaType.TEXT_HTML_VALUE)
public class SearchController {
  private final Logger logger = Logger.getLogger(SearchController.class.getName());

  @Autowired
  private BookService bookService;

  @GetMapping
  public String searchBook(@RequestParam(required = true) String title, Model model) {
    List<URLModel> results = Collections.emptyList();

    try {
      results = bookService.search(title);
    } catch (Exception e) {
      logger.log(Level.WARNING, "Warning: %s".formatted(e.getMessage()));
    }

    model.addAttribute("title", title);
    model.addAttribute("resultList", results);
    return "results";
  }

}
