package webserver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentType {
    TEXT_HTML("text/html;charset=utf-8");

    private final String detail;
}
