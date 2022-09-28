package memo;

import lombok.RequiredArgsConstructor;
import webserver.ContentType;
import webserver.HTMLMaker;
import webserver.Request;
import webserver.Response;

import java.io.IOException;

@RequiredArgsConstructor
public class MemoController {
    private final MemoFinder memoFinder;
    private final MemoWriter memoWriter;

    private final String INDEX_PATH = "/index.html";
    private final String LOGIN_PATH = "/user/login.html";
    private final static String STATIC_FILE_PATH = "./webapp";


    public Response writeMemo(Request request) {
        var userIdInCookie = request.getHeader().getCookie().getUserId();
        if (userIdInCookie.isEmpty()) {
            return Response.redirect(request.getStartLine().getProtocol(), LOGIN_PATH);
        }

        memoWriter.write(userIdInCookie.get(), request.getBody().get("contents"));

        return Response.redirect(request.getStartLine().getProtocol(), INDEX_PATH);
    }

    public Response getMemoListView(Request request) throws IOException {
        var memoList = memoFinder.findAll();

        return Response.okWithData(
                request.getStartLine().getProtocol(),
                ContentType.TEXT_HTML,
                HTMLMaker.makeMemoListView(STATIC_FILE_PATH + "/index.html", memoList).getBytes());
    }
}
