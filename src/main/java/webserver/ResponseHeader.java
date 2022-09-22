package webserver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ResponseHeader {
    private final Map<ResponseHeaderOption, String> contents;

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
