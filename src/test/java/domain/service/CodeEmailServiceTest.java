package domain.service;

import builders.UserBuilder;
import domain.entity.ActivationCode;
import domain.entity.Code;
import domain.entity.User;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.spring6.SpringTemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CodeEmailServiceTest {
    @Mock
    EmailService emailService;
    @Mock
    SpringTemplateEngine templateEngine;
    @InjectMocks
    CodeEmailService codeEmailService;

    @Test
    void shouldCallEmailService() throws MessagingException {

        String to = "test@email.com";
        String username = "example";
        User user = new UserBuilder().build();
        ActivationCode code = new ActivationCode(user);

        doReturn("").when(templateEngine).process(anyString(), any());

        codeEmailService.sendCodeEmail(to, username, code);

        verify(emailService, times(1)).sendEmail(any(), any(), any());
    }
}