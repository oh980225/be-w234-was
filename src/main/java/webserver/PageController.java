package webserver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageController {
    private final static String STATIC_FILE_PATH = "./webapp";

    public static Response getPage(Request request) throws IOException {
        var targetFile = PageLoader.getPageByPath(STATIC_FILE_PATH + request.getStartLine().getUrl().getPath());
        Protocol protocol = request.getStartLine().getProtocol();

        if (targetFile.isPresent()) {
            return Response.okWithData(
                    protocol,
                    ContentType.findByExtension(new Extension(targetFile.get().toPath().toString())),
                    Files.readAllBytes(targetFile.get().toPath()));
        }

        return Response.notFound(protocol);
    }
}
