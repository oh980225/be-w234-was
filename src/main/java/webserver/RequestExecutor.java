package webserver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestExecutor {
    private static Map<RequestMapping, RequestProcessable> mappers = new HashMap<>();

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
            return mappers.get(RequestMapping.findBy(
                    request.getRequestStartLine().getMethod(),
                    request.getRequestStartLine().getUrl().getPath())).process(request);
        } catch (WebServerException e) {
            return Response.badRequest(request.getRequestStartLine().getProtocol(), e.getMessage());
        }
    }
}