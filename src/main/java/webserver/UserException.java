package webserver;

public class UserException extends RuntimeException {
    public UserException(UserErrorMessage message) {
        super(message.getDetail());
    }
}
