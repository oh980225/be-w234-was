package webserver;

import lombok.Getter;

import java.util.Optional;

@Getter
public class Response {
    private final ResponseStatusLine statusLine;
    private Optional<ResponseHeader> header = Optional.empty();
    private Optional<byte[]> body = Optional.empty();

    private final static String NOT_FOUND_CONTENT = "NOT FOUND PAGE";

    private Response(ResponseStatusLine statusLine) {
        this.statusLine = statusLine;
    }

    private Response(ResponseStatusLine statusLine, ContentType contentType, byte[] body) {
        this.statusLine = statusLine;
        this.header = Optional.of(new ResponseHeader(Optional.of(contentType), body.length));
        this.body = Optional.of(body);
    }

    private Response(ResponseStatusLine statusLine, String location) {
        this.statusLine = statusLine;
        this.header = Optional.of(new ResponseHeader(location));
    }

    public static Response okWithData(Protocol protocol, ContentType contentType, byte[] body) {
        return new Response(new ResponseStatusLine(protocol, StatusCode.OK), contentType, body);
    }

    public static Response ok(Protocol protocol) {
        return new Response(new ResponseStatusLine(protocol, StatusCode.OK));
    }

    public static Response badRequest(Protocol protocol, String message) {
        return new Response(
                new ResponseStatusLine(protocol, StatusCode.BAD_REQUEST),
                ContentType.TEXT_HTML,
                message.getBytes());
    }

    public static Response notFound(Protocol protocol) {
        return new Response(
                new ResponseStatusLine(protocol, StatusCode.NOT_FOUND),
                ContentType.TEXT_HTML,
                NOT_FOUND_CONTENT.getBytes());
    }

    public static Response redirect(Protocol protocol, String location) {
        return new Response(
                new ResponseStatusLine(protocol, StatusCode.REDIRECT),
                location);
    }
}
