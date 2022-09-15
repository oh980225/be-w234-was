package webserver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageLoader {
    public static Response getPage(Request request) throws IOException {
        var targetFile = new File("./webapp" + request.getRequestStartLine().getUrl().getPath());

        if (notFoundFile(targetFile)) {
            return Response.notFound(request.getRequestStartLine().getProtocol());
        }

        return Response.ok(
                request.getRequestStartLine().getProtocol(),
                ContentType.TEXT_HTML,
                Files.readAllBytes(targetFile.toPath()));
    }

    private static boolean notFoundFile(File targetFile) {
        return !Files.isReadable(targetFile.toPath()) || Files.isDirectory(targetFile.toPath());
    }
}
