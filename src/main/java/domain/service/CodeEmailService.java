package domain.service;

import domain.entity.Code;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CodeEmailService {

    private final EmailService emailService;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendCodeEmail(
            String to,
            String username,
            Code code
    ) throws MessagingException {
        String templateName = code.getTemplateName();


        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", code.getURL());
        properties.put("activation_code", code.getCode());

        Context context = new Context();
        context.setVariables(properties);

        String template = templateEngine.process(templateName, context);

        emailService.sendEmail(to, code.getSubject(), template);
    }

}
