package domain.repository;

import domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(value = "SELECT * FROM message m WHERE (m.user_from_id = ?1 OR m.user_to_id = ?1) AND m.id > ?2;", nativeQuery = true)
    List<Message> getNewMessages(UUID userId, Long lastMessageReceived);

    @Query(value = "SELECT * FROM message WHERE m.user_from_id IN(?1, ?2) AND m.user_to_id IN(?1, ?2);", nativeQuery = true)
    List<Message> getMessagesByChat(UUID fistUser, UUID secondUser);

}