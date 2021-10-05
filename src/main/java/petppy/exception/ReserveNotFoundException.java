package petppy.exception;

public class ReserveNotFoundException extends RuntimeException {

    public ReserveNotFoundException() {
    }

    public ReserveNotFoundException(String message) {
        super(message);
    }

    public ReserveNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReserveNotFoundException(Throwable cause) {
        super(cause);
    }
}
