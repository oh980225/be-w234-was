package webserver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class Request {
    private final RequestStartLine startLine;
    private final RequestHeader header;
    @Getter
    private final Map<String, String> body;

    public HttpMethod getMethod() {
        return startLine.getMethod();
    }

    public Url getUrl() {
        return startLine.getUrl();
    }

    public Protocol getProtocol() {
        return startLine.getProtocol();
    }

    public int getContentLength() {
        return header.getContentLength();
    }

    public Cookie getCookie() {
        return header.getCookie();
    }
}
