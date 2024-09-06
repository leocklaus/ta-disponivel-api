package domain.service;

import domain.entity.Code;
import domain.exception.InvalidCodeException;
import domain.repository.CodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeService {

    private final CodeEmailService codeEmailService;
    private final CodeRepository codeRepository;

    @Transactional
    public void sendEmail(Code code) {

        code = saveCode(code);
        var user = code.getUser();

        try{
            codeEmailService.sendCodeEmail(user.getEmail(), user.getName(), code);
        }catch (Exception e){
            log.error(e.getMessage());
        }


    }

    @Transactional
    private Code saveCode(Code code){

        return codeRepository.save(code);

    }

    public Code findCode(String code){
        return codeRepository.findByCode(code)
                .orElseThrow(InvalidCodeException::new);
    }

}
