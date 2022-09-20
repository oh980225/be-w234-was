package webserver;

import lombok.Getter;

import java.util.Optional;

@Getter
public class Response {
    private final Protocol protocol;
    private final StatusCode statusCode;
    private Optional<ContentType> contentType = Optional.empty();
    private Optional<Integer> contentLength = Optional.empty();
    private Optional<byte[]> body = Optional.empty();

    private Optional<String> location = Optional.empty();

    private final static String NOT_FOUND_CONTENT = "NOT FOUND PAGE";

    private Response(Protocol protocol, StatusCode statusCode) {
        this.protocol = protocol;
        this.statusCode = statusCode;
    }

    private Response(Protocol protocol, StatusCode statusCode, ContentType contentType, byte[] body) {
        this(protocol, statusCode);
        this.body = Optional.of(body);
        this.contentType = Optional.of(contentType);
        this.contentLength = Optional.of(body.length);
    }

    private Response(Protocol protocol, StatusCode statusCode, String location) {
        this(protocol, statusCode);
        this.location = Optional.of(location);
    }

    public static Response okWithData(Protocol protocol, ContentType contentType, byte[] body) {
        return new Response(protocol, StatusCode.OK, contentType, body);
    }

    public static Response ok(Protocol protocol) {
        return new Response(protocol, StatusCode.OK);
    }

    public static Response badRequest(Protocol protocol, String message) {
        return new Response(protocol, StatusCode.BAD_REQUEST, ContentType.TEXT_HTML, message.getBytes());
    }

    public static Response notFound(Protocol protocol) {
        return new Response(protocol, StatusCode.NOT_FOUND, ContentType.TEXT_HTML, NOT_FOUND_CONTENT.getBytes());
    }

    public static Response redirect(Protocol protocol, String location) {
        return new Response(protocol, StatusCode.REDIRECT, location);
    }

    public boolean hasBody() {
        return getBody().isPresent() && getContentType().isPresent() && getContentLength().isPresent();
    }

    public boolean isRedirect() {
        return getLocation().isPresent();
    }
}
