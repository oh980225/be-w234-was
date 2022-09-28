package webserver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import memo.MemoController;
import memo.MemoFinder;
import memo.MemoJDBCAdapter;
import memo.MemoWriter;
import user.UserAuthProvider;
import user.UserController;
import user.UserFinder;
import user.UserJDBCAdapter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestExecutor {
    private final static Map<RequestMapping, RequestProcessable> mappers = new HashMap<>();
    private static UserController userController;
    private static MemoController memoController;

    static {
        configController();

        mappers.put(new RequestMapping(HttpMethod.GET, "/user/create"), userController::signUpForGet);
        mappers.put(new RequestMapping(HttpMethod.POST, "/user/create"), userController::signUpForPost);
        mappers.put(new RequestMapping(HttpMethod.POST, "/user/login"), userController::login);
        mappers.put(new RequestMapping(HttpMethod.GET, "/user/list"), userController::findAllUser);
        mappers.put(new RequestMapping(HttpMethod.GET, "/index.html"), memoController::getMemoListView);
        mappers.put(new RequestMapping(HttpMethod.POST, "/memo"), memoController::writeMemo);
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

    private static void configController() {
        var userAdapter = new UserJDBCAdapter();
        var memoAdapter = new MemoJDBCAdapter();

        userController = new UserController(new UserAuthProvider(userAdapter, userAdapter), new UserFinder(userAdapter));
        memoController = new MemoController(new MemoFinder(memoAdapter), new MemoWriter(memoAdapter, userAdapter));
    }
}