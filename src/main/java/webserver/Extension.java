package webserver;

import lombok.Getter;

@Getter
public class Extension {
    private final String extension;

    public Extension(String path) {
        var splitPath = path.split("/");
        var splitLastPath = splitPath[splitPath.length - 1].split("\\.");

        if (splitLastPath.length < 2) {
            throw new WebServerException(WebServerErrorMessage.NOT_EXIST_EXTENSION);
        }

        this.extension = splitLastPath[splitLastPath.length - 1];
    }
}
