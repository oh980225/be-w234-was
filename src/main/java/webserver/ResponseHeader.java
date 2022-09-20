package webserver;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ResponseHeader {
    private final Map<String, String> contents = new HashMap<>();
    private Optional<ContentType> contentType = Optional.empty();

    private final String CONTENT_LENGTH = "Content-Length";
    private final String CONTENT_TYPE = "Content-Type";
    private final String LOCATION = "Location";

    public ResponseHeader(Optional<ContentType> contentType, Integer contentLength) {
        contents.put(CONTENT_LENGTH, String.valueOf(contentLength));
        this.contentType = contentType;
    }

    public ResponseHeader(String location) {
        contents.put(LOCATION, location);
    }

    public int getContentLength() {
        var length = contents.get(CONTENT_LENGTH);
        return length == null ? 0 : Integer.parseInt(length);
    }

    @Override
    public String toString() {
        return (contentType.map(type -> CONTENT_TYPE + ": " + type.getDetail() + "\r\n").orElse("")) +
                (contents.get(CONTENT_LENGTH) != null ? CONTENT_LENGTH + ": " + contents.get(CONTENT_LENGTH) + "\r\n" : "") +
                (contents.get(LOCATION) != null ? LOCATION + ": " + contents.get(LOCATION) + "\r\n" : "");
    }
}
