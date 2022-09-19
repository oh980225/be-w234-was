package webserver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageLoader {
    private final static String STATIC_FILE_PATH = "./webapp";

    public static Response getPage(Request request) throws IOException {
        String path = request.getStartLine().getUrl().getPath();
        Protocol protocol = request.getStartLine().getProtocol();
        var targetFile = new File(STATIC_FILE_PATH + path);

        if (notFoundFile(targetFile)) {
            return Response.notFound(protocol);
        }

        return Response.okWithData(
                protocol,
                ContentType.findByExtension(new Extension(path)),
                Files.readAllBytes(targetFile.toPath()));
    }

    private static boolean notFoundFile(File targetFile) {
        return !Files.isReadable(targetFile.toPath()) || Files.isDirectory(targetFile.toPath());
    }
}
