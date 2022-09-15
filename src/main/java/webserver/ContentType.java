package webserver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentType {
    TEXT_HTML("text/html;charset=utf-8"),
    TEXT_CSS("text/css;charset=utf-8"),
    ;

    private final String detail;

    public static ContentType findByPath(String path) {
        String extension = getExtension(path);

        for (var contentType : ContentType.values()) {
            if (contentType.getDetail().contains(extension)) {
                return contentType;
            }
        }

        return TEXT_HTML;
    }

    private static String getExtension(String path) {
        var splitPath = path.split("/");
        var splitLastPath = splitPath[splitPath.length - 1].split("\\.");
        var extension = splitLastPath[splitLastPath.length - 1];
        return extension;
    }
}
