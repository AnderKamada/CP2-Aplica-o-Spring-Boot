package service;

import dto.AuthorRecord;
import dto.BookRecord;
import entity.Author;
import exception.ResourceNotFoundException;
import mapper.AuthorMapper;
import mapper.BookMapper;
import repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    // Injeção de dependências via construtor
    public AuthorServiceImpl(AuthorRepository authorRepository,
                             AuthorMapper authorMapper,
                             BookMapper bookMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional
    public AuthorRecord create(AuthorRecord authorRecord) {
        // Converte Record para Entity
        Author author = authorMapper.toEntity(authorRecord);

        // Salva no banco
        Author savedAuthor = authorRepository.save(author);

        // Converte Entity para Record e retorna
        return authorMapper.toRecord(savedAuthor);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorRecord findById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: "+ id));

        return authorMapper.toRecord(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorRecord> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toRecord)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AuthorRecord update(Long id, AuthorRecord authorRecord) {
        // Verifica se o autor existe
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author"));

        // Atualiza os campos
        existingAuthor.setName(authorRecord.name());
        existingAuthor.setEmail(authorRecord.email());

        // Salva as alterações
        Author updatedAuthor = authorRepository.save(existingAuthor);

        return authorMapper.toRecord(updatedAuthor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Verifica se existe antes de deletar
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author");
        }

        authorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookRecord> findBooksByAuthorId(Long authorId) {
        // Verifica se o autor existe
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author"));

        // Retorna os livros do autor
        return author.getBooks()
                .stream()
                .map(bookMapper::toRecord)
                .collect(Collectors.toList());
    }
}