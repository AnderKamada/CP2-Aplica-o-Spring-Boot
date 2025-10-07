package controller;

import dto.BookRecord;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /** POST /books - Cria um novo livro (201) */
    @PostMapping
    public ResponseEntity<BookRecord> create(@Valid @RequestBody BookRecord dto) {
        BookRecord saved = bookService.create(dto);   // instancia, não estático
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** GET /books - Lista todos os livros (200) */
    @GetMapping
    public ResponseEntity<List<BookRecord>> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    /** GET /books/{id} - Busca por ID (200/404) */
    @GetMapping("/{id}")
    public ResponseEntity<BookRecord> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    /** PUT /books/{id} - Atualiza um livro (200/404) */
    @PutMapping("/{id}")
    public ResponseEntity<BookRecord> update(@PathVariable Long id,
                                             @Valid @RequestBody BookRecord dto) {
        BookRecord updated = bookService.update(id, dto);  // instancia, não estático
        return ResponseEntity.ok(updated);
    }

    /** DELETE /books/{id} - Remove um livro (204/404) */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
