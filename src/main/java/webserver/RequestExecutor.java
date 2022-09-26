package webserver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import user.UserController;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestExecutor {
    private static Map<RequestMapping, RequestProcessable> mappers = new HashMap<>();

    static {
        mappers.put(new RequestMapping(HttpMethod.GET, "/user/create"), UserController::signUpForGet);
        mappers.put(new RequestMapping(HttpMethod.POST, "/user/create"), UserController::signUpForPost);
        mappers.put(new RequestMapping(HttpMethod.POST, "/user/login"), UserController::login);
        mappers.put(new RequestMapping(HttpMethod.GET, "/user/list"), UserController::findAllUser);
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