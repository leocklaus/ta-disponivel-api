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

    @Enumerated(EnumType.STRING)
    private CodeType codeType;

    public ActivationCode(User user){
        super(user);
        this.codeType = CodeType.ACTIVATION;
        generateCode(5);
    }

}
