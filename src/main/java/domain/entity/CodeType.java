package domain.entity;

import lombok.Getter;

@Getter
public enum CodeType {
    ACTIVATION("activation",
            "frontend-url/activate",
            "activate_account",
            "Ative a sua conta"
    ),
    PASSWORD_RECOVER(
            "password_recover",
            "frontend-url/recover-password",
            "recover_password",
            "Recupere a sua senha"
    );

    private final String name;
    private final String url;
    private final String templateName;
    private final String subject;

    CodeType(String name, String url, String templateName, String subject) {
        this.name = name;
        this.url = url;
        this.templateName = templateName;
        this.subject = subject;
    }

}
