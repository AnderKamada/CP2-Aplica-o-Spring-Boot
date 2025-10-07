package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRecord(
        Long id,
        @NotBlank String title,
        @NotBlank String isbn,
        @NotNull  Long authorId
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