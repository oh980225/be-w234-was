package webserver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import user.UserAuthProvider;
import user.UserController;
import user.UserFinder;
import user.UserJDBCAdapter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestExecutor {
    private final static Map<RequestMapping, RequestProcessable> mappers = new HashMap<>();

    static {
        UserController userController = getUserController();
        mappers.put(new RequestMapping(HttpMethod.GET, "/user/create"), userController::signUpForGet);
        mappers.put(new RequestMapping(HttpMethod.POST, "/user/create"), userController::signUpForPost);
        mappers.put(new RequestMapping(HttpMethod.POST, "/user/login"), userController::login);
        mappers.put(new RequestMapping(HttpMethod.GET, "/user/list"), userController::findAllUser);
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

    private static UserController getUserController() {
        var userAdapter = new UserJDBCAdapter();
        return new UserController(new UserAuthProvider(userAdapter, userAdapter), new UserFinder(userAdapter));
    }
}