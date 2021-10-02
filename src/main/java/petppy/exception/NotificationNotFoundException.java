package petppy.exception;

public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException() {
    }

    public NotificationNotFoundException(String message) {
        super(message);
    }

    public NotificationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotificationNotFoundException(Throwable cause) {
        super(cause);
    }
}
