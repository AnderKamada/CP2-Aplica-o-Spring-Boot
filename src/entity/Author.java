package entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um autor que pode ter vários livros
 */
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // Relacionamento: Um autor tem muitos livros
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    // Construtores
    public Author() {
    }

    public Author(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    // Método auxiliar para adicionar livros
    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }

    // Método auxiliar para remover livros
    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }
}
