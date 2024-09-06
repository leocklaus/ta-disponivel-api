package domain.repository;

import domain.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReplyRepository extends JpaRepository<Reply, UUID> {
}