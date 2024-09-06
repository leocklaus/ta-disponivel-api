package domain.service;

import builders.UserBuilder;
import domain.entity.ActivationCode;
import domain.entity.Code;
import domain.entity.User;
import domain.exception.InvalidCodeException;
import domain.repository.CodeRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CodeServiceTest {

    @Mock
    private CodeEmailService codeEmailService;

    @Mock
    private CodeRepository codeRepository;

    @Captor
    private ArgumentCaptor<String> emailCaptor;

    @Captor
    private ArgumentCaptor<String> nameCaptor;

    @Captor
    private ArgumentCaptor<Code> codeCaptor;

    @Captor
    private ArgumentCaptor<String> stringCodeCapture;

    @InjectMocks
    @Spy
    private CodeService codeService;

    @Test
    void shouldSendEmail() throws MessagingException {

        User user = new UserBuilder().build();
        Code code = new ActivationCode(user);
        UUID codeId = UUID.randomUUID();
        code.setId(codeId);

        doReturn(code).when(codeRepository).save(any());
        doNothing().when(codeEmailService).sendCodeEmail(
                emailCaptor.capture(), nameCaptor.capture(), codeCaptor.capture());

        codeService.sendEmail(code);

        verify(codeEmailService, times(1)).sendCodeEmail(any(), any(), any());

        assertThat(emailCaptor.getValue())
                .isEqualTo(user.getEmail());

        assertThat(nameCaptor.getValue())
                .isEqualTo(user.getName());

        assertThat(codeCaptor.getValue())
                .isEqualTo(code);
    }

    @Test
    void shouldReturnCodeIfExists(){

        User user = new UserBuilder().build();
        Code code = new ActivationCode(user);
        UUID codeId = UUID.randomUUID();
        code.setId(codeId);

        doReturn(Optional.of(code)).when(codeRepository).findByCode(stringCodeCapture.capture());

        Code returnedCode = codeService.findCode("abcde");

        assertThat(returnedCode)
                .isEqualTo(code);

        assertThat(stringCodeCapture.getValue())
                .isEqualTo("abcde");

        verify(codeRepository, times(1)).findByCode(any());

    }

    @Test
    void shouldThrowExceptionIfCodeNotExists(){

        doReturn(Optional.empty()).when(codeRepository).findByCode(any());

        assertThrows(InvalidCodeException.class, ()->{
            codeService.findCode("abcde");
        });

        verify(codeRepository, times(1)).findByCode(any());

    }

}