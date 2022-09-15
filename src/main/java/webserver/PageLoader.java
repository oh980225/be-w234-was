package webserver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageLoader {
    public static Response getPage(Request request) throws IOException {
        String path = request.getRequestStartLine().getUrl().getPath();
        Protocol protocol = request.getRequestStartLine().getProtocol();
        var targetFile = new File("./webapp" + path);

        if (notFoundFile(targetFile)) {
            return Response.notFound(protocol);
        }

        return Response.ok(
                protocol,
                ContentType.findByExtension(new Extension(path)),
                Files.readAllBytes(targetFile.toPath()));
    }

    private static boolean notFoundFile(File targetFile) {
        return !Files.isReadable(targetFile.toPath()) || Files.isDirectory(targetFile.toPath());
    }
}
