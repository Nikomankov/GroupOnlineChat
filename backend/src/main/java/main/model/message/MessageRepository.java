package main.model.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m WHERE m.dateTime < :last ORDER BY m.dateTime DESC LIMIT 10")
    List<Message> getMessagesOlderThanLast(@Param("last") LocalDateTime last);
}
