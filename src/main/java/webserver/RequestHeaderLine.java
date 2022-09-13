package webserver;

public class RequestHeaderLine {
    private final String[] tokens;

    public RequestHeaderLine(String readLine) {
        if (readLine == null || readLine.equals("")) {
            throw new WebServerException(WebServerErrorMessage.EMPTY_REQUEST);
        }

        String[] tokens = readLine.split(" ");

        if (tokens.length != 3) {
            throw new WebServerException(WebServerErrorMessage.INVALID_FORMAT);
        }

        this.tokens = tokens;
    }

    public String[] getTokens() {
        return tokens;
    }
}
