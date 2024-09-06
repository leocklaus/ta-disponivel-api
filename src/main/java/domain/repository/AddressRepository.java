package domain.repository;

import domain.entity.Address;
import domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findAddressByUser(User user);
}
