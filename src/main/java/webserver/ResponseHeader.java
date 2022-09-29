package webserver;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ResponseHeader {
    private final Map<ResponseHeaderOption, String> contents = new HashMap<>();

    public void putContents(ResponseHeaderOption key, String value) {
        contents.put(key, value);
    }

    @Override
    public String toString() {
        String headerString = "";

        for (Map.Entry<ResponseHeaderOption, String> entry : contents.entrySet()) {
            headerString += (contents.get(entry.getKey()) != null
                    ? entry.getKey().getDetail() + ": " + entry.getValue() + "\r\n"
                    : "");
        }

        return headerString;
    }
}
