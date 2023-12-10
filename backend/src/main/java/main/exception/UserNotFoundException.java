package main.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String user){
        super("User " + user + " associated with this message is not in the database");
    }
}
