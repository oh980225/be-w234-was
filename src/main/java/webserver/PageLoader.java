package webserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PageLoader {
    private PageLoader() {
    }

    public static Response getPage(Request request) throws IOException {
        var targetFile = new File("./webapp" + request.getUrl());

        if (notFoundFile(targetFile)) {
            return Response.notFound(request.getProtocol());
        }

        byte[] body = Files.readAllBytes(targetFile.toPath());
        return Response.ok(request.getProtocol(), ContentType.TEXT_HTML, body);
    }

    private static boolean notFoundFile(File targetFile) {
        return !Files.isReadable(targetFile.toPath()) || Files.isDirectory(targetFile.toPath());
    }
}
