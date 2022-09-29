package webserver;

import com.google.common.base.Strings;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class RequestStartLine {
    private final HttpMethod method;

    private final Url url;

    private final Protocol protocol;

    private static final Pattern REGEX = Pattern.compile("(GET|get|POST|post)\\s(\\/\\S*)\\s(HTTP/1.1)");

    public RequestStartLine(String readLine) {
        if (Strings.isNullOrEmpty(readLine)) {
            throw new WebServerException(WebServerErrorMessage.EMPTY_REQUEST);
        }

        var matcher = REGEX.matcher(readLine);
        if (!matcher.matches()) {
            throw new WebServerException(WebServerErrorMessage.INVALID_FORMAT);
        }

        method = HttpMethod.valueOf(matcher.group(1));
        url = new Url(matcher.group(2));
        protocol = Protocol.findByName(matcher.group(3));
    }
}
