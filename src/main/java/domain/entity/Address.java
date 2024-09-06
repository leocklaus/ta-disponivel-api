package domain.entity;

import api.dto.AddressInput;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(nullable = false)
    private CEP CEP;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String neighbourhood;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private Integer number;
    private String extraInfo;
    @Cascade(CascadeType.ALL)
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public void update(AddressInput input){
        setCEP(input.cep());
        setStreet(input.street());
        setNeighbourhood(input.neighbourhood());
        setCity(input.city());
        setState(input.state());
        setNumber(input.number());
        setExtraInfo(input.extraInfo());
    }
}
