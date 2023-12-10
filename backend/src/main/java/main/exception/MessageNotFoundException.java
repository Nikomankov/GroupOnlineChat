package main.exception;

public class MessageNotFoundException extends Exception{
    public MessageNotFoundException(){
        super("Messages not found. Maybe problem with request or database is empty");
    }
    public MessageNotFoundException(int id){
        super("Message " + id + " not found. Maybe problem with request or database is empty");
    }
}
