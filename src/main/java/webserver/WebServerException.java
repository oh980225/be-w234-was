package webserver;

class WebServerException extends RuntimeException {
    public WebServerException(WebServerErrorMessage message) {
        super(message.getDetail());
    }
}
