package webserver;

enum WebServerErrorMessage {
    EMPTY_REQUEST("요청이 비어있습닏다."),
    INVALID_FORMAT("잘못된 형식의 요청입니다."),
    INVALID_PROTOCOL("지원하지 않는 프로토콜입니다."),
    ;

    WebServerErrorMessage(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    private final String detail;
}
