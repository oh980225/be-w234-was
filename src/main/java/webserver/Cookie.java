package webserver;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class Cookie {
    private final Map<String, String> contents;

    private final String LOGINED = "logined";

    boolean isLogined() {
        return Boolean.parseBoolean(contents.get(LOGINED));
    }
}
