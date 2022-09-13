package webserver;

public class RequestHeader {
    private final HttpMethod method;

    private final String url;
    private final Protocol protocol;


    public RequestHeader(RequestHeaderLine requestHeaderLine) {
        String[] tokens = requestHeaderLine.getTokens();
        method = HttpMethod.valueOf(tokens[0]);
        url = tokens[1];
        protocol = Protocol.findByValue(tokens[2]);
    }

    public String getUrl() {
        return url;
    }
}
