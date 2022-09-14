package webserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PageLoader {
    private PageLoader() {
    }

    public static byte[] getPage(Request request) throws IOException {
        var targetFile = new File("./webapp" + request.getUrl().getPath());

        if (notFoundFile(targetFile)) {
            throw new WebServerException(WebServerErrorMessage.NOT_FOUND);
        }

        return Files.readAllBytes(targetFile.toPath());
    }

    private static boolean notFoundFile(File targetFile) {
        return !Files.isReadable(targetFile.toPath()) || Files.isDirectory(targetFile.toPath());
    }
}
