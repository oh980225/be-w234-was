package webserver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ResponseHeader {
    private final Map<String, String> contents;

    @Override
    public String toString() {
        String headerString = "";

        for (Map.Entry<String, String> entry : contents.entrySet()) {
            headerString += (contents.get(entry.getKey()) != null ? entry.getKey() + ": " + entry.getValue() + "\r\n" : "");
        }

        return headerString;
    }
}
