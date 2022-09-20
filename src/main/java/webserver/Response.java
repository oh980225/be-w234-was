package webserver;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
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

    private Response(ResponseStatusLine statusLine, Map<String, String> headerMap) {
        this(statusLine);
        this.header = Optional.of(new ResponseHeader(headerMap));
    }

    private Response(ResponseStatusLine statusLine, Map<String, String> headerMap, byte[] body) {
        this(statusLine, headerMap);
        this.body = Optional.of(body);
    }

    public static Response okWithData(Protocol protocol, ContentType contentType, byte[] body) {
        Map<String, String> headerMap = getHeaderMapForContent(contentType, body.length);

        return new Response(new ResponseStatusLine(protocol, StatusCode.OK), headerMap, body);
    }

    public static Response ok(Protocol protocol) {
        return new Response(new ResponseStatusLine(protocol, StatusCode.OK));
    }

    public static Response badRequest(Protocol protocol, String message) {
        Map<String, String> headerMap = getHeaderMapForContent(ContentType.TEXT_HTML, message.getBytes().length);

        return new Response(
                new ResponseStatusLine(protocol, StatusCode.BAD_REQUEST),
                headerMap,
                message.getBytes());
    }

    public static Response notFound(Protocol protocol) {
        Map<String, String> headerMap = getHeaderMapForContent(ContentType.TEXT_HTML, NOT_FOUND_CONTENT.getBytes().length);

        return new Response(
                new ResponseStatusLine(protocol, StatusCode.NOT_FOUND),
                headerMap,
                NOT_FOUND_CONTENT.getBytes());
    }

    public static Response redirect(Protocol protocol, String location) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Location", location);

        return new Response(
                new ResponseStatusLine(protocol, StatusCode.REDIRECT),
                headerMap);
    }

    public static Response redirectWithCookie(Protocol protocol, String location, String cookieContents) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Set-Cookie", cookieContents);
        headerMap.put("Location", location);

        return new Response(
                new ResponseStatusLine(protocol, StatusCode.REDIRECT),
                headerMap);
    }

    private static Map<String, String> getHeaderMapForContent(ContentType contentType, int contentLength) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", contentType.getDetail());
        headerMap.put("Content-Length", String.valueOf(contentLength));
        return headerMap;
    }
}
