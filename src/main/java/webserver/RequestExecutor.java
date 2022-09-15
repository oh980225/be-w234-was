package webserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestExecutor {
    private static Map<RequestMapping, ResponseCreator> mappers = new HashMap<>();

    static {
        mappers.put(RequestMapping.PAGE_LOAD, request -> {
            try {
                return PageLoader.getPage(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        mappers.put(RequestMapping.GET_SIGN_UP, request -> UserAuthProvider.signUpForGet(request));
    }

    public static Response execute(Request request) {
        try {
            return mappers.get(RequestMapping.valueOf(request.getMethod(), request.getUrl().getPath())).create(request);
        } catch (WebServerException e) {
            return Response.badRequest(request.getProtocol(), e.getMessage());
        }
    }
}