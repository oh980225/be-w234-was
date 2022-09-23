package webserver;

import java.io.IOException;

public interface RequestProcessable {
    Response process(Request request) throws IOException;
}
