package service;

import dto.BookRecord;
import java.util.List;

public interface BookService {
    BookRecord create(BookRecord dto);
    List<BookRecord> findAll();
    BookRecord findById(Long id);
    BookRecord update(Long id, BookRecord dto);
    void delete(Long id);
}
