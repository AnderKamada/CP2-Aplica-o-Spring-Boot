package mapper;

import dto.AuthorRecord;
import entity.Author;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorMapperImplTest {

    private final AuthorMapper mapper = new AuthorMapperImpl();

    @Test
    void toRecord_ShouldConvertEntityToRecord() {
        // Arrange
        Author author = new Author("John Doe", "john@example.com");
        author.setId(1L);

        // Act
        AuthorRecord record = mapper.toRecord(author);

        // Assert
        assertNotNull(record);
        assertEquals(1L, record.id());
        assertEquals("John Doe", record.name());
        assertEquals("john@example.com", record.email());
    }

    @Test
    void toEntity_ShouldConvertRecordToEntity() {
        // Arrange
        AuthorRecord record = new AuthorRecord(1L, "Jane Doe", "jane@example.com");

        // Act
        Author author = mapper.toEntity(record);

        // Assert
        assertNotNull(author);
        assertEquals(1L, author.getId());
        assertEquals("Jane Doe", author.getName());
        assertEquals("jane@example.com", author.getEmail());
    }

    @Test
    void toRecord_WithNull_ShouldReturnNull() {
        assertNull(mapper.toRecord(null));
    }

    @Test
    void toEntity_WithNull_ShouldReturnNull() {
        assertNull(mapper.toEntity(null));
    }
}