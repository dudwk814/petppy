package petppy.exception;

public class EnquiryNotFoundException extends RuntimeException {

    public EnquiryNotFoundException() {
    }

    public EnquiryNotFoundException(String message) {
        super(message);
    }

    public EnquiryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnquiryNotFoundException(Throwable cause) {
        super(cause);
    }
}
