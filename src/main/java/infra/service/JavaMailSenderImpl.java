package infra.service;

import domain.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
public class JavaMailSenderImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendEmail(String to, String subject, String template) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    MULTIPART_MODE_MIXED,
                    UTF_8.name()
            );

            helper.setFrom("leocklaus@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);

            helper.setText(template, true);

            mailSender.send(mimeMessage);
        }catch (MessagingException e) {
            throw new RuntimeException(e);
        } ;




    }
}
