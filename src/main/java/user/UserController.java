package user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import webserver.*;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserController {
    private static final String INDEX_PATH = "/index.html";
    private static final String LOGIN_PATH = "/user/login.html";
    private static final String LOGIN_FAIL_PATH = "/user/login_failed.html";
    private static final String USER_LIST_PATH = "webapp/user/list.html";
    private static final String LOGIN_SUCCESS_COOKIE = "logined=true; Path=/";
    private static final String LOGIN_FAIL_COOKIE = "logined=false; Path=/";

    public static Response signUpForGet(Request request) {
        try {
            var query = request.getStartLine().getUrl().getQuery();

            UserAuthProvider.signUp(SignUpRequest.builder()
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

    public static Response signUpForPost(Request request) {
        try {
            var body = request.getBody();

            UserAuthProvider.signUp(SignUpRequest.builder()
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

    public static Response login(Request request) {
        try {
            var body = request.getBody();

            UserAuthProvider.login(new LoginRequest(body.get("userId"), body.get("password")));

            return Response.redirectWithCookie(request.getStartLine().getProtocol(), INDEX_PATH, LOGIN_SUCCESS_COOKIE);
        } catch (UserException e) {
            return Response.redirectWithCookie(request.getStartLine().getProtocol(), LOGIN_FAIL_PATH, LOGIN_FAIL_COOKIE);
        }
    }

    public static Response findAllUser(Request request) throws IOException {
        if (!request.getHeader().getCookie().isLogined()) {
            return Response.redirect(request.getStartLine().getProtocol(), LOGIN_PATH);
        }

        var allUser = UserFinder.findAll();

        return Response.okWithData(
                request.getStartLine().getProtocol(),
                ContentType.TEXT_HTML,
                HTMLTableMaker.makeUserTable(USER_LIST_PATH, allUser).getBytes());
    }
}
