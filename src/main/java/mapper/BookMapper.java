package mapper;

import dto.BookRecord;
import entity.Book;

public interface BookMapper {
    BookRecord toRecord(Book book);
    Book toEntity(BookRecord record);
}
