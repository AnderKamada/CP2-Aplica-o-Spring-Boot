package dto;

public record BookRecord(
        Long id,
        String title,
        String isbn,
        Long authorId
) {
    // Validação customizada
    public BookRecord {
        if (title != null && title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
        if (isbn != null && isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be blank");
        }
    }
}