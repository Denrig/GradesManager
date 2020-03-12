package Exceptions;


public class ValidationException extends RuntimeException {
    /**
     *
     * @param message-string
     */
    public ValidationException(String message) { super(message); }
}
