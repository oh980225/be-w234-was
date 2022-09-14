package webserver;

public enum Protocol {
    HTTP_1_1("HTTP/1.1"),
    ;

    Protocol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private final String name;

    public static Protocol findByValue(String value) {
        for(var protocol : Protocol.values()) {
            if(value.equals(protocol.getName())) {
                return protocol;
            }
        }

        throw new WebServerException(WebServerErrorMessage.INVALID_PROTOCOL);
    }
}
