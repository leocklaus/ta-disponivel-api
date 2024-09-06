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

    public PasswordRecoverCode(User user){
        super(user, CodeType.PASSWORD_RECOVER);
        generateCode(5);
        setTenMinutesDuration();
    }
}
