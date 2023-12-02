package main.model.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.model.user.User;

import java.time.LocalDateTime;


    /*
    TODO:
        -add a variable "dialogId" that determines which dialog the message belongs to
    */

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private LocalDateTime dateTime;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Message(String content, User user){
        this.content = content;
        this.dateTime = LocalDateTime.now();
        this.user = user;
    }
    public Message(String message){
        this.content = message;
        this.dateTime = LocalDateTime.now();
    }
}
