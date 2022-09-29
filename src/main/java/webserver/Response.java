package webserver;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.DataOutputStream;
import java.io.IOException;
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
        return Response.builder()
                .statusLine(new ResponseStatusLine(protocol, StatusCode.OK))
                .header(makeHeaderWithContentTypeAndBody(contentType, body))
                .body(body)
                .build();
    }

    public static Response ok(Protocol protocol) {
        return Response.builder()
                .statusLine(new ResponseStatusLine(protocol, StatusCode.OK))
                .build();
    }

    public static Response badRequest(Protocol protocol, String message) {
        return Response.builder()
                .statusLine(new ResponseStatusLine(protocol, StatusCode.BAD_REQUEST))
                .header(makeHeaderWithContentTypeAndBody(ContentType.TEXT_HTML, message.getBytes()))
                .body(message.getBytes())
                .build();
    }

    public static Response notFound(Protocol protocol) {
        return Response.builder()
                .statusLine(new ResponseStatusLine(protocol, StatusCode.NOT_FOUND))
                .header(makeHeaderWithContentTypeAndBody(ContentType.TEXT_HTML, NOT_FOUND_CONTENT.getBytes()))
                .body(NOT_FOUND_CONTENT.getBytes())
                .build();
    }

    public static Response redirect(Protocol protocol, String location) {
        var header = new ResponseHeader();
        header.putContents(ResponseHeaderOption.LOCATION, location);

        return Response.builder()
                .statusLine(new ResponseStatusLine(protocol, StatusCode.REDIRECT))
                .header(header)
                .build();
    }

    public static Response redirectWithCookie(Protocol protocol, String location, String cookieContents) {
        var header = new ResponseHeader();
        header.putContents(ResponseHeaderOption.SET_COOKIE, cookieContents);
        header.putContents(ResponseHeaderOption.LOCATION, location);


        return Response.builder()
                .statusLine(new ResponseStatusLine(protocol, StatusCode.REDIRECT))
                .header(header)
                .build();
    }

    public static Response serverError(Protocol protocol, String message) {
        return Response.builder()
                .statusLine(new ResponseStatusLine(protocol, StatusCode.SERVER_ERROR))
                .header(makeHeaderWithContentTypeAndBody(ContentType.TEXT_HTML, message.getBytes()))
                .body(message.getBytes())
                .build();
    }

    public Optional<ResponseHeader> getHeader() {
        return Optional.ofNullable(header);
    }

    public Optional<byte[]> getBody() {
        return Optional.ofNullable(body);
    }

    public void flush(DataOutputStream dos) throws IOException {
        writeStatusLine(dos);
        writeHeader(dos);
        writeBody(dos);
        dos.flush();
    }

    private void writeStatusLine(DataOutputStream dos) throws IOException {
        dos.writeBytes(statusLine.toString() + "\r\n");
    }

    private void writeHeader(DataOutputStream dos) throws IOException {
        if (getHeader().isPresent()) {
            dos.writeBytes(header.toString());
        }
    }

    private void writeBody(DataOutputStream dos) throws IOException {
        if (getBody().isPresent() && getHeader().isPresent()) {
            dos.writeBytes("\r\n");
            dos.write(body,
                    0,
                    Integer.parseInt(header.getContents().get(ResponseHeaderOption.CONTENT_LENGTH)));
        }
    }

    private static ResponseHeader makeHeaderWithContentTypeAndBody(ContentType contentType, byte[] body) {
        var header = new ResponseHeader();
        header.putContents(ResponseHeaderOption.CONTENT_TYPE, contentType.getDetail());
        header.putContents(ResponseHeaderOption.CONTENT_LENGTH, String.valueOf(body.length));
        return header;
    }
}
