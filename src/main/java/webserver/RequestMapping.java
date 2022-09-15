package webserver;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RequestMapping {
    PAGE_LOAD(HttpMethod.GET, ""),
    GET_SIGN_UP(HttpMethod.GET, "/user/create"),
    ;

    private final HttpMethod method;
    private final String path;

    static RequestMapping valueOf(HttpMethod method, String path) {
        for (var mapping : RequestMapping.values()) {
            if (mapping.method == method && path.equals(mapping.path)) {
                return mapping;
            }
        }

        return PAGE_LOAD;
    }
}
