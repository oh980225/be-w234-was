package webserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PageLoader {
    private PageLoader() {
    }

    public static Response getPage(Request request) throws IOException {
        var targetFile = new File("./webapp" + request.getUrl().getPath());

        if (notFoundFile(targetFile)) {
            return Response.notFound(request.getProtocol());
        }

        return Response.ok(
                request.getProtocol(),
                ContentType.TEXT_HTML,
                Files.readAllBytes(targetFile.toPath()));
    }

    private static boolean notFoundFile(File targetFile) {
        return !Files.isReadable(targetFile.toPath()) || Files.isDirectory(targetFile.toPath());
    }
}
