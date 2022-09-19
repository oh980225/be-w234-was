package webserver;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class Extension {
    private final String extension;

    private static final Pattern REGEX = Pattern.compile("(.+)\\.(.+)");

    public Extension(String path) {
        var matcher = REGEX.matcher(path);

        if (!matcher.matches()) {
            throw new WebServerException(WebServerErrorMessage.NOT_EXIST_EXTENSION);
        }

        this.extension = matcher.group(2);
    }
}
