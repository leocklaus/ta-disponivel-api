package domain.service;

public interface EmailService {
    void sendEmail(
            String to,
            String subject,
            String template);
}
