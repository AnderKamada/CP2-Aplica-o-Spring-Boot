package repository;

import entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Método customizado para buscar livros por autor
    List<Book> findByAuthorId(Long authorId);

    // Método para buscar por ISBN
    boolean existsByIsbn(String isbn);
}