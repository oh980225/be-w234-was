package webserver;

public class RequestHeaderLine {
    private final String[] tokens;

    public RequestHeaderLine(String readLine) {
        if (readLine == null || readLine.equals("")) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        String[] tokens = readLine.split(" ");

        if (tokens.length != 3) {
            throw new IllegalArgumentException("형식이 일치하지 않습니다.");
        }

        this.tokens = tokens;
    }

    public String[] getTokens() {
        return tokens;
    }
}
