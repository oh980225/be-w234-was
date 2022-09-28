package memo;

import lombok.RequiredArgsConstructor;
import webserver.Request;
import webserver.Response;

@RequiredArgsConstructor
public class MemoController {
    private final MemoFinder memoFinder;
    private final MemoWriter memoWriter;

    private final String INDEX_PATH = "/index.html";
    private final String LOGIN_PATH = "/user/login.html";

    public Response writeMemo(Request request) {
        var userIdInCookie = request.getHeader().getCookie().getUserId();
        if (userIdInCookie.isEmpty()) {
            return Response.redirect(request.getStartLine().getProtocol(), LOGIN_PATH);
        }

        memoWriter.write(userIdInCookie.get(), request.getBody().get("contents"));

        return Response.redirect(request.getStartLine().getProtocol(), INDEX_PATH);
    }
}
