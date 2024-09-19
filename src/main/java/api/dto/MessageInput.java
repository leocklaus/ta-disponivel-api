package api.dto;

import java.util.UUID;

public record MessageInput(
        UUID userId,
        String message
) {
}
