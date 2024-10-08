package domain.repository;

import domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u from User u WHERE u.email = ?1")
    UserDetails findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
