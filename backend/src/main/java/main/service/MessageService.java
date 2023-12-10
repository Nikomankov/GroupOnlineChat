package main.service;

import main.model.message.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService {

    public List<Message> getAllMessages();
    public Message sendMessage(Message message);
    public Message getMessage(int id);
    public Message updateMessage(Message message, int id);
    public Message deleteMessage(int id);
    public boolean deleteMessageForever(int id);
    public List<Message> getNextOlderMessages(LocalDateTime converted);
}
