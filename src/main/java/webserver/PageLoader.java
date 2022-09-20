package webserver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageLoader {
    public static Optional<File> getPageByPath(String path) {
        var targetFile = new File(path);

        if (notFoundFile(targetFile)) {
            return Optional.empty();
        }

        return Optional.of(targetFile);
    }

    private static boolean notFoundFile(File targetFile) {
        return !Files.isReadable(targetFile.toPath()) || Files.isDirectory(targetFile.toPath());
    }
}
