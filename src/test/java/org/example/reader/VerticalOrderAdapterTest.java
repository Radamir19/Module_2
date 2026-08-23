package org.example.reader;

import org.example.exceptions.OrderParseException;
import org.example.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class VerticalOrderAdapterTest {
    private final VerticalOrderAdapter adapter = new VerticalOrderAdapter();
    @TempDir
    Path tempDir;

    @Test
    void testRegularParse() throws IOException {
        List<String> input = List.of(
                "2021-02-09T16:00:22|Industrial|8800",
                "2021-02-09T08:42:59|Power Engineer|17480",
                "2021-02-09T10:48:34|Mosque|33120"
        );
        Path path = tempDir.resolve("adapter.txt");
        Files.write(path, input);
        List<Order> result = adapter.parse(path.toString());
        List<Order> orders = List.of(new Order(LocalDateTime.parse("2021-02-09T16:00:22"), "Industrial", 8800),
                new Order(LocalDateTime.parse("2021-02-09T08:42:59"), "Power Engineer", 17480),
                new Order(LocalDateTime.parse("2021-02-09T10:48:34"), "Mosque", 33120));
        Assertions.assertEquals(orders, result);
    }

    @Test
    void testEmptyFile() throws IOException {
        Path path = tempDir.resolve("adapter.txt");
        Files.write(path, List.of());
        Assertions.assertEquals(List.of(), adapter.parse(path.toString()));
    }

    @Test
    void testCorruptedFile() throws IOException {
        Path path = tempDir.resolve("adapter.txt");
        List<String> input = List.of(
                "2021-02-09T16:00:22|Industrial",
                "2021-02-09T08:42:59|Power Engineer|17480",
                "2021-02-09T10:48:34|Mosque|33120"
        );
        Files.write(path, input);
        Assertions.assertThrows(OrderParseException.class, () -> adapter.parse(path.toString()));
    }

    @Test
    void testOrderParseException() {
        Path path = tempDir.resolve("adapter");
        Assertions.assertThrows(OrderParseException.class, () -> adapter.parse(path.toString()));
    }
}
