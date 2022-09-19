package webserver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {
    OK(200, "OK"),
    NOT_FOUND(404, "NOT FOUND"),
    BAD_REQUEST(400, "BAD REQUEST"),
    ;

    private final int code;
    private final String message;
}
