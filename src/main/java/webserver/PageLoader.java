package webserver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.nio.file.Files;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageLoader {
    public static File getPageByPath(String path) {
        var targetFile = new File(path);

        if (notFoundFile(targetFile)) {
            throw new WebServerException(WebServerErrorMessage.NOT_FOUND);
        }

        return targetFile;
    }

    private static boolean notFoundFile(File targetFile) {
        return !Files.isReadable(targetFile.toPath()) || Files.isDirectory(targetFile.toPath());
    }
}
