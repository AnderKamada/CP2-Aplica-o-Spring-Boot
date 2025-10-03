package service;

import dto.AuthorRecord;
import dto.BookRecord;

import java.util.List;

public interface AuthorService {
    AuthorRecord create(AuthorRecord authorRecord);
    AuthorRecord findById(Long id);
    List<AuthorRecord> findAll();
    AuthorRecord update(Long id, AuthorRecord authorRecord);
    void delete(Long id);
    List<BookRecord> findBooksByAuthorId(Long authorId);
}