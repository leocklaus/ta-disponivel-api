package domain.entity;

import lombok.Getter;

@Getter
public enum NotificationType {
    RESERVED("Reserved", "reservou o item para você."),
    DONATED("Doação", "doou o item para você"),
    QUESTION("Pergunta", "Adicionou uma pergunta"),
    ANSWER("Resposta", "Respondeu a pergunta");

    private final String name;
    private final String message;

    NotificationType(String name, String message) {
        this.name = name;
        this.message = message;
    }


}
