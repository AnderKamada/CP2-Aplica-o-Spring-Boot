package dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthorRecord(
        Long id,
        @NotBlank String name,
        @Email @NotBlank String email
) {
    // Validação customizada no construtor compacto
    public AuthorRecord {
        if (name != null && name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (email != null && email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }
    }
}
