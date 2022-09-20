package webserver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserController {
    private static final String INDEX_PATH = "/index.html";

    public static Response signUpForGet(Request request) {
        var query = request.getStartLine().getUrl().getQuery();

        UserAuthProvider.signUp(SignUpRequest.builder()
                .userId(query.get("userId"))
                .password(query.get("password"))
                .name(query.get("name"))
                .email(query.get("email"))
                .build());

        return Response.ok(request.getStartLine().getProtocol());
    }

    public static Response signUpForPost(Request request) {
        var body = request.getBody();

        UserAuthProvider.signUp(SignUpRequest.builder()
                .userId(body.get("userId"))
                .password(body.get("password"))
                .name(body.get("name"))
                .email(body.get("email"))
                .build());

        return Response.redirect(request.getStartLine().getProtocol(), INDEX_PATH);
    }
}
