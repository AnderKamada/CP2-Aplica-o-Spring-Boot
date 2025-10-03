package service;

import dto.AuthorRecord;
import entity.Author;
import exception.ResourceNotFoundException;
import mapper.AuthorMapper;
import mapper.BookMapper;
import repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author;
    private AuthorRecord authorRecord;

    @BeforeEach
    void setUp() {
        author = new Author("John Doe", "john@example.com");
        author.setId(1L);

        authorRecord = new AuthorRecord(1L, "John Doe", "john@example.com");
    }

    @Test
    void create_ShouldReturnCreatedAuthor() {
        // Arrange
        when(authorMapper.toEntity(any(AuthorRecord.class))).thenReturn(author);
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        when(authorMapper.toRecord(any(Author.class))).thenReturn(authorRecord);

        // Act
        AuthorRecord result = authorService.create(authorRecord);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.name());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void findById_WhenAuthorExists_ShouldReturnAuthor() {
        // Arrange
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.toRecord(author)).thenReturn(authorRecord);

        // Act
        AuthorRecord result = authorService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("John Doe", result.name());
    }

    @Test
    void findById_WhenAuthorNotExists_ShouldThrowException() {
        // Arrange
        when(authorRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            authorService.findById(999L);
        });
    }

    @Test
    void findAll_ShouldReturnListOfAuthors() {
        // Arrange
        List<Author> authors = Arrays.asList(author);
        when(authorRepository.findAll()).thenReturn(authors);
        when(authorMapper.toRecord(any(Author.class))).thenReturn(authorRecord);

        // Act
        List<AuthorRecord> result = authorService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void delete_WhenAuthorExists_ShouldDeleteSuccessfully() {
        // Arrange
        when(authorRepository.existsById(1L)).thenReturn(true);

        // Act
        authorService.delete(1L);

        // Assert
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_WhenAuthorNotExists_ShouldThrowException() {
        // Arrange
        when(authorRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            authorService.delete(999L);
        });
    }
}
