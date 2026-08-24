package org.example.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderTest {
    private final FileReader reader = new FileReader();

    @TempDir
    Path tempDir;

    @Test
    void testReadFile() throws IOException {
        Path path = tempDir.resolve("reader.txt");
        List<String> expectedLines = List.of("CCC|100|5", "ABC|50|3");
        Files.write(path, expectedLines);
        Assertions.assertEquals(expectedLines, reader.loadLines(path.toString()));
    }

    @Test
    void testReadEmptyFile() throws IOException {
        Path path = tempDir.resolve("reader.txt");
        Files.write(path, List.of());
        Assertions.assertEquals(List.of(), reader.loadLines(path.toString()));
    }

    @Test
    void testUnknownFile() {
        Path path = tempDir.resolve("unknown.txt");
        Assertions.assertThrows(IOException.class, () -> reader.loadLines(path.toString()));
    }
}
