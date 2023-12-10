package main.model.message;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('JOIN', 'LEAVE', 'MESSAGE', 'DELETE')")
    private MessageType type;
    private String content;
    private String sender;
    private String receiver;
    private LocalDateTime dateTime;
}
