package controller;

import dto.BookRecord;
import service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * POST /books - Cria um novo livro
     * @return 201 Created
     */
    @PostMapping
    public ResponseEntity<BookRecord> create(@RequestBody BookRecord bookRecord) {
        BookRecord created = bookService.create(bookRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * GET /books - Lista todos os livros
     * @return 200 OK
     */
    @GetMapping
    public ResponseEntity<List<BookRecord>> findAll() {
        List<BookRecord> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    /**
     * GET /books/{id} - Busca livro por ID
     * @return 200 OK ou 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookRecord> findById(@PathVariable Long id) {
        BookRecord book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    /**
     * PUT /books/{id} - Atualiza um livro
     * @return 200 OK ou 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookRecord> update(
            @PathVariable Long id,
            @RequestBody BookRecord bookRecord) {
        BookRecord updated = bookService.update(id, bookRecord);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /books/{id} - Deleta um livro
     * @return 204 No Content ou 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
