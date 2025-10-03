package service.impl;

import dto.BookRecord;
import entity.Author;
import entity.Book;
import jakarta.persistence.EntityNotFoundException;
import mapper.BookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.AuthorRepository;
import repository.BookRepository;
import service.BookService;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;
    private final BookMapper mapper;

    public BookServiceImpl(BookRepository bookRepo, AuthorRepository authorRepo, BookMapper mapper) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.mapper = mapper;
    }

    @Override
    public BookRecord create(BookRecord dto) {
        Book book = mapper.toEntity(dto);
        if (dto.authorId() != null) {
            Author author = authorRepo.findById(dto.authorId())
                    .orElseThrow(() -> new EntityNotFoundException("Author not found: " + dto.authorId()));
            book.setAuthor(author);
        } else {
            book.setAuthor(null);
        }
        book = bookRepo.save(book);
        return mapper.toRecord(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookRecord> findAll() {
        return bookRepo.findAll().stream().map(mapper::toRecord).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookRecord findById(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
        return mapper.toRecord(book);
    }

    @Override
    public BookRecord update(Long id, BookRecord dto) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));

        book.setTitle(dto.title());
        book.setIsbn(dto.isbn());

        if (dto.authorId() != null) {
            Author author = authorRepo.findById(dto.authorId())
                    .orElseThrow(() -> new EntityNotFoundException("Author not found: " + dto.authorId()));
            book.setAuthor(author);
        } else {
            book.setAuthor(null);
        }

        return mapper.toRecord(bookRepo.save(book));
    }

    @Override
    public void delete(Long id) {
        if (!bookRepo.existsById(id)) {
            throw new EntityNotFoundException("Book not found: " + id);
        }
        bookRepo.deleteById(id);
    }
}
