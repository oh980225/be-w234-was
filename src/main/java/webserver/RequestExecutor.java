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
                return PageController.getPage(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        mappers.put(RequestMapping.GET_SIGN_UP, request -> UserController.signUpForGet(request));

        mappers.put(RequestMapping.POST_SIGN_UP, request -> UserController.signUpForPost(request));
    }

    public static Response execute(Request request) {
        try {
            return mappers.get(RequestMapping.findBy(
                    request.getStartLine().getMethod(),
                    request.getStartLine().getUrl().getPath())).process(request);
        } catch (WebServerException e) {
            return Response.badRequest(request.getStartLine().getProtocol(), e.getMessage());
        }
    }
}