package webserver;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class RequestMapping {
    private final HttpMethod method;
    private final String path;
}
