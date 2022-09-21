package webserver;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class Response {
    @Getter
    private final ResponseStatusLine statusLine;
    private final ResponseHeader header;
    private final byte[] body;

    private final static String NOT_FOUND_CONTENT = "NOT FOUND PAGE";

    public static Response okWithData(Protocol protocol, ContentType contentType, byte[] body) {
        var headerMap = getHeaderMapForContent(contentType, body.length);

        return Response.builder()
                .statusLine(new ResponseStatusLine(protocol, StatusCode.OK))
                .header(new ResponseHeader(headerMap))
                .body(body)
                .build();
    }

    public static Response ok(Protocol protocol) {
        return Response.builder()
                .statusLine(new ResponseStatusLine(protocol, StatusCode.OK))
                .build();
    }

    public static Response badRequest(Protocol protocol, String message) {
        var headerMap =
                getHeaderMapForContent(ContentType.TEXT_HTML, message.getBytes().length);

        return Response.builder()
                .statusLine(new ResponseStatusLine(protocol, StatusCode.BAD_REQUEST))
                .header(new ResponseHeader(headerMap))
                .body(message.getBytes())
                .build();
    }

    public static Response notFound(Protocol protocol) {
        var headerMap =
                getHeaderMapForContent(ContentType.TEXT_HTML, NOT_FOUND_CONTENT.getBytes().length);

        return Response.builder()
                .statusLine(new ResponseStatusLine(protocol, StatusCode.NOT_FOUND))
                .header(new ResponseHeader(headerMap))
                .body(NOT_FOUND_CONTENT.getBytes())
                .build();
    }

    public static Response redirect(Protocol protocol, String location) {
        Map<ResponseHeaderOption, String> headerMap = new HashMap<>();
        headerMap.put(ResponseHeaderOption.LOCATION, location);

        return Response.builder()
                .statusLine(new ResponseStatusLine(protocol, StatusCode.REDIRECT))
                .header(new ResponseHeader(headerMap))
                .build();
    }

    public static Response redirectWithCookie(Protocol protocol, String location, String cookieContents) {
        Map<ResponseHeaderOption, String> headerMap = new HashMap<>();
        headerMap.put(ResponseHeaderOption.SET_COOKIE, cookieContents);
        headerMap.put(ResponseHeaderOption.LOCATION, location);


        return Response.builder()
                .statusLine(new ResponseStatusLine(protocol, StatusCode.REDIRECT))
                .header(new ResponseHeader(headerMap))
                .build();
    }

    public Optional<ResponseHeader> getHeader() {
        return Optional.ofNullable(header);
    }

    public Optional<byte[]> getBody() {
        return Optional.ofNullable(body);
    }

    private static Map<ResponseHeaderOption, String> getHeaderMapForContent(ContentType contentType, int contentLength) {
        Map<ResponseHeaderOption, String> headerMap = new HashMap<>();
        headerMap.put(ResponseHeaderOption.CONTENT_TYPE, contentType.getDetail());
        headerMap.put(ResponseHeaderOption.CONTENT_LENGTH, String.valueOf(contentLength));

        return headerMap;
    }
}
