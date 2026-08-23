package org.example.writer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.io.TempDir;

public class FileWriterTest {

    private final FileResultWriter writer = new FileResultWriter();
    @TempDir
    Path tempDir;

    @Test
    void writeResultToFile() throws IOException {
        Map<String, Double> map = new HashMap<>();
        map.put("test", 100.05);
        Path path = tempDir.resolve("test.txt");
        writer.write(map, path.toString());
        Assertions.assertEquals(List.of("test - 100.05"), Files.readAllLines(path));
    }

    @Test
    void testEmptyFileToWrite() throws IOException {
        Map<String, Double> map = new HashMap<>();
        Path path = tempDir.resolve("test.txt");
        writer.write(map, path.toString());
        Assertions.assertEquals(List.of(), Files.readAllLines(path));
    }

    @Test
    void testIOExceptionThrow() {
        String path = tempDir.resolve("no_dir").resolve("test.txt").toString();
        Map<String, Double> map = new HashMap<>();
        map.put("test", 100.05);
        Assertions.assertThrows(IOException.class, () -> writer.write(map, path));
    }
}
