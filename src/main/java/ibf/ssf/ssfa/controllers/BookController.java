package ibf.ssf.ssfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;

import ibf.ssf.ssfa.service.BookService;
import ibf.ssf.ssfa.models.BookModel;
import static ibf.ssf.ssfa.Constants.*;

@Controller
@RequestMapping(path = "/book", produces = MediaType.TEXT_HTML_VALUE)
public class BookController {

  @Autowired
  @Qualifier(BOOK_CACHE)
  private BookService bookService;

  @GetMapping("/{bookKey}")
  public String getBook(Model model, @PathVariable String bookKey) {
    BookModel book = bookService.getBookByKey(bookKey);
    model.addAttribute("book", book);

    return "displaybook";
  }
}
