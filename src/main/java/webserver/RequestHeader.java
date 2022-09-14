package webserver;

public class RequestHeader {
    private final HttpMethod method;

    private final String url;
    private final Protocol protocol;


    public RequestHeader(RequestStartLine requestStartLine) {
        String[] tokens = requestStartLine.getTokens();
        method = HttpMethod.valueOf(tokens[0]);
        url = tokens[1];
        protocol = Protocol.findByValue(tokens[2]);
    }

    public String getUrl() {
        return url;
    }
}
