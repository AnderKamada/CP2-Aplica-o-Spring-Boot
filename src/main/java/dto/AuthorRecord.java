package dto;

public record AuthorRecord(
        Long id,
        String name,
        String email
) {
    // Validação customizada no construtor compacto (opcional)
    public AuthorRecord {
        if (name != null && name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (email != null && email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }
    }
}
