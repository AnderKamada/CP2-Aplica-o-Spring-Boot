package controller;

import dto.AuthorRecord;
import dto.BookRecord;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /** POST /authors - Cria um novo autor (201) */
    @PostMapping
    public ResponseEntity<AuthorRecord> create(@Valid @RequestBody AuthorRecord authorRecord) {
        AuthorRecord created = authorService.create(authorRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** GET /authors - Lista todos os autores (200) */
    @GetMapping
    public ResponseEntity<List<AuthorRecord>> findAll() {
        List<AuthorRecord> authors = authorService.findAll();
        return ResponseEntity.ok(authors);
    }

    /** GET /authors/{id} - Busca autor por ID (200/404) */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorRecord> findById(@PathVariable Long id) {
        AuthorRecord author = authorService.findById(id);
        return ResponseEntity.ok(author);
    }

    /** PUT /authors/{id} - Atualiza um autor (200/404) */
    @PutMapping("/{id}")
    public ResponseEntity<AuthorRecord> update(@PathVariable Long id,
                                               @Valid @RequestBody AuthorRecord authorRecord) {
        AuthorRecord updated = authorService.update(id, authorRecord);
        return ResponseEntity.ok(updated);
    }

    /** DELETE /authors/{id} - Deleta um autor (204/404) */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** GET /authors/{id}/books - Lista livros de um autor (200) */
    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookRecord>> findBooksByAuthor(@PathVariable Long id) {
        List<BookRecord> books = authorService.findBooksByAuthorId(id);
        return ResponseEntity.ok(books);
    }
}
