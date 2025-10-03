package mapper;

import dto.BookRecord;
import entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookRecord toRecord(Book book) {
        if (book == null) {
            return null;
        }
        return new BookRecord(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getAuthor() != null ? book.getAuthor().getId() : null
        );
    }

    @Override
    public Book toEntity(BookRecord record) {
        if (record == null) {
            return null;
        }
        Book book = new Book();
        book.setId(record.id());
        book.setTitle(record.title());
        book.setIsbn(record.isbn());
        // O author será setado no Service
        return book;
    }
}