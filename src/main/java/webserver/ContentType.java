package webserver;

public enum ContentType {
    TEXT_HTML("text/html;charset=utf-8")
    ;

    ContentType(String detail) {
        this.detail = detail;
    }

    private final String detail;

    public String getDetail() {
        return detail;
    }
}
