package main.exception;

public class MessageNotDeletedException extends Exception{
    public MessageNotDeletedException(int id){
        super("Message " + id + " was not deleted");
    }
}
