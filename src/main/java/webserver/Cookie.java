package webserver;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class Cookie {
    private final Map<String, String> contents;

    private final String USER_ID = "userId";

    public Optional<String> getUserId() {
        return Optional.ofNullable(contents.get(USER_ID));
    }
}
