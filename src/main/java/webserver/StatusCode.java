package webserver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    BAD_REQUEST(400, "Bad Request"),
    REDIRECT(302, "Found"),
    SERVER_ERROR(500, "Internal Server Error")
    ;

    private final int code;
    private final String message;
}
