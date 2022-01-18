package ibf.ssf.ssfa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ibf.ssf.ssfa.models.BookModel;
import ibf.ssf.ssfa.models.URLModel;

@Service
public interface BookService {

  public List<URLModel> search(String title);

  public BookModel getBookByKey(String bookKey);

}
