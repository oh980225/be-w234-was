package webserver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Request {
    private final RequestStartLine startLine;
    private final RequestHeader header;
    private final Map<String, String> body;
}
