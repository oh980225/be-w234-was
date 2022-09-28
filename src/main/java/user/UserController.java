package user;

import lombok.RequiredArgsConstructor;
import webserver.ContentType;
import webserver.HTMLTableMaker;
import webserver.Request;
import webserver.Response;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class UserController {
    private final String INDEX_PATH = "/index.html";
    private final String LOGIN_PATH = "/user/login.html";
    private final String LOGIN_FAIL_PATH = "/user/login_failed.html";
    private final String USER_LIST_PATH = "webapp/user/list.html";
    private final UserAuthProvider userAuthProvider;
    private final UserFinder userFinder;

    public Response signUpForGet(Request request) {
        try {
            var query = request.getStartLine().getUrl().getQuery();

            userAuthProvider.signUp(SignUpRequest.builder()
                    .userId(query.get("userId"))
                    .password(query.get("password"))
                    .name(query.get("name"))
                    .email(query.get("email"))
                    .build());
            return Response.ok(request.getStartLine().getProtocol());
        } catch (UserException e) {
            return Response.badRequest(request.getStartLine().getProtocol(), e.getMessage());
        }
    }

    public Response signUpForPost(Request request) {
        try {
            var body = request.getBody();

            userAuthProvider.signUp(SignUpRequest.builder()
                    .userId(body.get("userId"))
                    .password(body.get("password"))
                    .name(body.get("name"))
                    .email(body.get("email"))
                    .build());

            return Response.redirect(request.getStartLine().getProtocol(), INDEX_PATH);
        } catch (UserException e) {
            return Response.badRequest(request.getStartLine().getProtocol(), e.getMessage());
        }
    }

    public Response login(Request request) {
        try {
            var body = request.getBody();

            userAuthProvider.login(new LoginRequest(body.get("userId"), body.get("password")));

            return Response.redirectWithCookie(request.getStartLine().getProtocol(), INDEX_PATH, makeUserIdCookie(body));
        } catch (UserException e) {
            return Response.redirect(request.getStartLine().getProtocol(), LOGIN_FAIL_PATH);
        }
    }

    public Response findAllUser(Request request) throws IOException {
        if (request.getHeader().getCookie().getUserId().isEmpty()) {
            return Response.redirect(request.getStartLine().getProtocol(), LOGIN_PATH);
        }

        var allUser = userFinder.findAll();

        return Response.okWithData(
                request.getStartLine().getProtocol(),
                ContentType.TEXT_HTML,
                HTMLTableMaker.makeUserTable(USER_LIST_PATH, allUser).getBytes());
    }

    private static String makeUserIdCookie(Map<String, String> body) {
        return "userId=" + body.get("userId") + "; Path=/";
    }
}
