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
        StringBuilder headerString = new StringBuilder();

        for (Map.Entry<ResponseHeaderOption, String> entry : contents.entrySet()) {
            headerString.append(contents.get(entry.getKey()) != null
                    ? String.format("%s: %s\n", entry.getKey().getDetail(), entry.getValue())
                    : "");
        }

        return headerString.toString();
    }
}
