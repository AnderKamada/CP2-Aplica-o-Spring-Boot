package controller;

import dto.AuthorRecord;
import dto.BookRecord;
import service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * POST /authors - Cria um novo autor
     * @return 201 Created com o autor criado
     */
    @PostMapping
    public ResponseEntity<AuthorRecord> create(@RequestBody AuthorRecord authorRecord) {
        AuthorRecord created = authorService.create(authorRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * GET /authors - Lista todos os autores
     * @return 200 OK com lista de autores
     */
    @GetMapping
    public ResponseEntity<List<AuthorRecord>> findAll() {
        List<AuthorRecord> authors = authorService.findAll();
        return ResponseEntity.ok(authors);
    }

    /**
     * GET /authors/{id} - Busca autor por ID
     * @return 200 OK ou 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorRecord> findById(@PathVariable Long id) {
        AuthorRecord author = authorService.findById(id);
        return ResponseEntity.ok(author);
    }

    /**
     * PUT /authors/{id} - Atualiza um autor
     * @return 200 OK ou 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<AuthorRecord> update(
            @PathVariable Long id,
            @RequestBody AuthorRecord authorRecord) {
        AuthorRecord updated = authorService.update(id, authorRecord);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /authors/{id} - Deleta um autor
     * @return 204 No Content ou 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /authors/{id}/books - Lista livros de um autor
     * @return 200 OK com lista de livros
     */
    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookRecord>> findBooksByAuthor(@PathVariable Long id) {
        List<BookRecord> books = authorService.findBooksByAuthorId(id);
        return ResponseEntity.ok(books);
    }
}
