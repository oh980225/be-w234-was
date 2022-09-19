package webserver;

import com.google.common.base.Strings;
import lombok.Getter;

@Getter
public class RequestStartLine {
    private final HttpMethod method;

    private final Url url;

    private final Protocol protocol;

    public RequestStartLine(String readLine) {
        if (Strings.isNullOrEmpty(readLine)) {
            throw new WebServerException(WebServerErrorMessage.EMPTY_REQUEST);
        }

        String[] tokens = readLine.split(" ");

        if (tokens.length != 3) {
            throw new WebServerException(WebServerErrorMessage.INVALID_FORMAT);
        }

        method = HttpMethod.valueOf(tokens[0]);
        url = new Url(tokens[1]);
        protocol = Protocol.findByValue(tokens[2]);
    }
}
