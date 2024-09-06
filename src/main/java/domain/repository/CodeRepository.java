package domain.repository;

import domain.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CodeRepository extends JpaRepository<Code, UUID> {
    Optional<Code> findByCode(String code);
}
