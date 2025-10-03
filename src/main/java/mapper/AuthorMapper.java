package mapper;

import dto.AuthorRecord;
import entity.Author;

public interface AuthorMapper {
    AuthorRecord toRecord(Author author);
    Author toEntity(AuthorRecord record);
}