package domain.entity;

import domain.exception.UserInvalidEmailException;
import domain.exception.UserInvalidPasswordException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Setter
    private UUID id;
    @Setter
    @Column(nullable = false)
    private String firstName;
    @Setter
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public User(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        setEmail(email);
        setPassword(password);
    }

    public String getName(){
        return this.firstName + " " + this.getLastName();
    }

    public void setEmail(String email) {

        if(isEmailInvalid(email)){
            throw new UserInvalidEmailException();
        }

        this.email = email;
    }

    public void setPassword(String password) {

        if(isPasswordInvalid(password)){
            throw new UserInvalidPasswordException();
        }

        this.password = password;
    }

    private boolean isPasswordInvalid(String password){

        //Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

        Matcher m = p.matcher(password);

        return !m.matches();

    }

    private boolean isEmailInvalid(String email){
        Pattern p = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher m = p.matcher(email);

        return !m.matches();
    }


}
