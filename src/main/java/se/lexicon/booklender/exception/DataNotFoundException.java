package se.lexicon.booklender.exception;

public class DataNotFoundException extends Exception {

    private String message;

    public DataNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage(){

        message = message + " is not found.";
        return message;
    }
}
