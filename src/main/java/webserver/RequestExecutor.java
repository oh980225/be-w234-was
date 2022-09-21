package webserver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestExecutor {
    private static Map<RequestMapping, RequestProcessable> mappers = new HashMap<>();

    static {
        mappers.put(new RequestMapping(HttpMethod.GET, "/user/create"), request -> UserController.signUpForGet(request));
        mappers.put(new RequestMapping(HttpMethod.POST, "/user/create"), request -> UserController.signUpForPost(request));
        mappers.put(new RequestMapping(HttpMethod.POST, "/user/login"), request -> UserController.login(request));
    }

    public static Response execute(Request request) {
        try {
            var requestMapping = new RequestMapping(
                    request.getStartLine().getMethod(),
                    request.getStartLine().getUrl().getPath());

            if (mappers.get(requestMapping) == null) {
                return PageController.getPage(request);
            }

            return mappers.get(requestMapping).process(request);
        } catch (Exception e) {
            return Response.serverError(request.getStartLine().getProtocol(), e.getMessage());
        }
    }
}