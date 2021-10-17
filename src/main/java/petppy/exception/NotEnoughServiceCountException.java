package petppy.exception;

public class NotEnoughServiceCountException extends RuntimeException {

    public NotEnoughServiceCountException() {
    }

    public NotEnoughServiceCountException(String message) {
        super(message);
    }

    public NotEnoughServiceCountException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughServiceCountException(Throwable cause) {
        super(cause);
    }
}
