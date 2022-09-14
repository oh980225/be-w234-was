package webserver;

public class Request {
    private final HttpMethod method;

    private final String url;

    private final Protocol protocol;


    public Request(RequestStartLine requestStartLine) {
        String[] tokens = requestStartLine.getTokens();
        method = HttpMethod.valueOf(tokens[0]);
        url = tokens[1];
        protocol = Protocol.findByValue(tokens[2]);
    }

    public String getUrl() {
        return url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Protocol getProtocol() {
        return protocol;
    }
}
