package main.service;

import main.model.message.Message;
import main.model.message.MessageRepository;
import main.model.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;
    SimpMessagingTemplate messagingTemplate;

    public MessageServiceImpl(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message sendMessage(Message message) {
        System.out.println("content = " + message.getContent());
        int id = messageRepository.save(message).getId();
        message.setDateTime(LocalDateTime.now());
        message.setId(id);
        return message;
    }

    @Override
    public Message getMessage(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }
        return null;
    }

    @Override
    public Message updateMessage(Message message, int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent()){
            Message prevMessage = optionalMessage.get();
            if(message.getContent() != null){
                prevMessage.setContent(message.getContent());
            }
            prevMessage.setType(message.getType());
            messageRepository.save(prevMessage);
            return prevMessage;
        }
        return null;
    }

    @Override
    public Message deleteMessage(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(!optionalMessage.isPresent()){
            Message message = optionalMessage.get();
            message.setType(MessageType.DELETE);
            return message;
        }
        return null;
    }

    @Override
    public boolean deleteMessageForever(int id) {
        messageRepository.deleteById(id);
        Optional<Message> optionalMessage = messageRepository.findById(id);
        return !optionalMessage.isPresent();
    }

    @Override
    public List<Message> getNextOlderMessages(LocalDateTime last) {
        List<Message> messages = messageRepository.getMessagesOlderThanLast(last);
        if(messages.size()>0){
            return messages;
        }
        return null;
    }
}
