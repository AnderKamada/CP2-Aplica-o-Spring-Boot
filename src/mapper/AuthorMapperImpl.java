package mapper;

import dto.AuthorRecord;
import entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorRecord toRecord(Author author) {
        if (author == null) {
            return null;
        }
        return new AuthorRecord(
                author.getId(),
                author.getName(),
                author.getEmail()
        );
    }

    @Override
    public Author toEntity(AuthorRecord record) {
        if (record == null) {
            return null;
        }
        Author author = new Author();
        author.setId(record.id());
        author.setName(record.name());
        author.setEmail(record.email());
        return author;
    }
}
