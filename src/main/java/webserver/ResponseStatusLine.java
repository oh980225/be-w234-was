package webserver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseStatusLine {
    private final Protocol protocol;
    private final StatusCode statusCode;

    @Override
    public String toString() {
        return protocol.getName() + " " + statusCode.getCode() + " " + statusCode.getMessage();
    }
}
