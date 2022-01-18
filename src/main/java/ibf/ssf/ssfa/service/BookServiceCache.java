package ibf.ssf.ssfa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf.ssf.ssfa.models.BookModel;
import ibf.ssf.ssfa.models.URLModel;
import ibf.ssf.ssfa.repository.BookRepository;
import static ibf.ssf.ssfa.Constants.*;

@Service(BOOK_CACHE)
public class BookServiceCache implements BookService {

  @Autowired
  private BookServiceImple delegate;

  @Autowired
  BookRepository bookRepo;

  @Override
  public List<URLModel> search(String title) {
    return delegate.search(title);
  }

  public BookModel getBookByKey(String bookKey) {
    BookModel book;
    String bookAsString = bookRepo.getBook(bookKey);
    if (bookAsString == null) {
      book = delegate.getBookByKey(bookKey);
      bookRepo.save(bookKey, book.toString());
    } else {
      String[] bookParams = bookAsString.split("\\|");
      book = new BookModel(bookParams);
    }
    return book;

  }
}
