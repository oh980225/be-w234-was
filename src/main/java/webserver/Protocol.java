package webserver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Protocol {
    HTTP_1_1("HTTP/1.1"),
    ;

    private final String name;

    public static Protocol findByName(String name) {
        for(var protocol : Protocol.values()) {
            if(name.equals(protocol.getName())) {
                return protocol;
            }
        }

        throw new WebServerException(WebServerErrorMessage.INVALID_PROTOCOL);
    }
}
