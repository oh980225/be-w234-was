package webserver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageController {
    private final static String STATIC_FILE_PATH = "./webapp";

    public static Response getPage(Request request) throws IOException {
        try {
            var targetFile = PageLoader.getPageByPath(STATIC_FILE_PATH + request.getUrl().getPath());

            return Response.okWithData(
                    request.getProtocol(),
                    ContentType.findByExtension(new Extension(targetFile.toPath().toString())),
                    Files.readAllBytes(targetFile.toPath()));
        } catch (WebServerException e) {
            return Response.notFound(request.getProtocol());
        }
    }
}
