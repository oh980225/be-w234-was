package webserver;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class RequestHeader {
    private final Map<String, String> content;
    private final String CONTENT_LENGTH = "Content-Length";

    public int getContentLength() {
        var length = content.get(CONTENT_LENGTH);
        return length == null ? 0 : Integer.parseInt(length);
    }
}
