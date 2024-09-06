package domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ActivationCode extends Code{

    public ActivationCode(User user){
        super(user, CodeType.ACTIVATION);
        generateCode(5);
        setFiveMinutesDuration();
    }

}
