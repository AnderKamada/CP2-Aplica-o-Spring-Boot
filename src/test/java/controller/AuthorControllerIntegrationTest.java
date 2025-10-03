package controller;

import app.Checkpoint2Application;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AuthorRecord;
import entity.Author;
import org.springframework.test.context.ActiveProfiles;
import repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Teste de Integração completo
 * Testa o fluxo completo: Controller -> Service -> Repository -> Database
 */
@SpringBootTest(classes = app.Checkpoint2Application.class)
@AutoConfigureMockMvc
@org.springframework.test.context.ActiveProfiles("test")
class AuthorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
    }

    @Test
    void shouldListAuthors() throws Exception {
        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void createAuthor_ShouldReturnCreatedAuthor() throws Exception {
        // Arrange
        AuthorRecord newAuthor = new AuthorRecord(null, "Jane Doe", "jane@example.com");

        // Act & Assert
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAuthor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane@example.com"));
    }

    @Test
    void findAllAuthors_ShouldReturnListOfAuthors() throws Exception {
        // Arrange
        Author author1 = new Author("Author 1", "author1@example.com");
        Author author2 = new Author("Author 2", "author2@example.com");
        authorRepository.save(author1);
        authorRepository.save(author2);

        // Act & Assert
        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Author 1"))
                .andExpect(jsonPath("$[1].name").value("Author 2"));
    }

    @Test
    void findAuthorById_WhenExists_ShouldReturnAuthor() throws Exception {
        // Arrange
        Author author = new Author("Test Author", "test@example.com");
        Author saved = authorRepository.save(author);

        // Act & Assert
        mockMvc.perform(get("/authors/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.name").value("Test Author"));
    }

    @Test
    void findAuthorById_WhenNotExists_ShouldReturn404() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/authors/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Author not found with id: 999"));
    }

    @Test
    void updateAuthor_ShouldReturnUpdatedAuthor() throws Exception {
        // Arrange
        Author author = new Author("Old Name", "old@example.com");
        Author saved = authorRepository.save(author);

        AuthorRecord updateData = new AuthorRecord(
                saved.getId(),
                "New Name",
                "new@example.com"
        );

        // Act & Assert
        mockMvc.perform(put("/authors/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.email").value("new@example.com"));
    }

    @Test
    void deleteAuthor_ShouldReturnNoContent() throws Exception {
        // Arrange
        Author author = new Author("To Delete", "delete@example.com");
        Author saved = authorRepository.save(author);

        // Act & Assert
        mockMvc.perform(delete("/authors/" + saved.getId()))
                .andExpect(status().isNoContent());

        // Verify deletion
        mockMvc.perform(get("/authors/" + saved.getId()))
                .andExpect(status().isNotFound());
    }
}
