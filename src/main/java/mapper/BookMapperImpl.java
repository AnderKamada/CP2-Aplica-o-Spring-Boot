package mapper;

import dto.BookRecord;
import entity.Author;
import entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookRecord toRecord(Book book) {
        if (book == null) return null;
        Long authorId = (book.getAuthor() != null) ? book.getAuthor().getId() : null;
        return new BookRecord(book.getId(), book.getTitle(), book.getIsbn(), authorId);
    }

    @Override
    public Book toEntity(BookRecord dto) {
        if (dto == null) return null;
        Book book = new Book();
        book.setId(dto.id());
        book.setTitle(dto.title());
        book.setIsbn(dto.isbn());
        if (dto.authorId() != null) {
            Author a = new Author();
            a.setId(dto.authorId()); // referência “stub”; o service pode trocar por entidade gerenciada
            book.setAuthor(a);
        }
        return book;
    }
}
