package webserver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@Getter
@RequiredArgsConstructor
public enum ContentType {
    TEXT_HTML("text/html;charset=utf-8"),
    TEXT_CSS("text/css;charset=utf-8"),
    ;

    private final String detail;

    public static ContentType findByExtension(Extension extension) {
        for (var contentType : ContentType.values()) {
            if (contentType.getDetail().contains(extension.getExtension())) {
                return contentType;
            }
        }

        return TEXT_HTML;
    }
}
