package domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PasswordRecoverCode extends Code{
    @Enumerated(EnumType.STRING)
    private CodeType codeType;

    public PasswordRecoverCode(User user){
        super(user);
        this.codeType = CodeType.PASSWORD_RECOVER;
        generateCode(5);
    }
}
