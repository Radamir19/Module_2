package org.example.reader;

import org.example.exceptions.OrderParseException;
import org.example.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VerticalOrderAdapterTest {
    @Mock
    FileReader reader;
    @InjectMocks
    VerticalOrderAdapter adapter;

    @Test
    void testRegularParse() throws IOException {
        String path = "adapter.txt";
        List<String> input = List.of("2021-02-09T16:00:22|Industrial|8800", "2021-02-09T08:42:59|Power Engineer|17480", "2021-02-09T10:48:34|Mosque|33120");
        when(reader.loadLines(path)).thenReturn(input);
        List<Order> orders = List.of(new Order(LocalDateTime.parse("2021-02-09T16:00:22"), "Industrial", 8800),
                new Order(LocalDateTime.parse("2021-02-09T08:42:59"), "Power Engineer", 17480),
                new Order(LocalDateTime.parse("2021-02-09T10:48:34"), "Mosque", 33120));
        List<Order> result = adapter.parse(path);
        Assertions.assertEquals(orders, result);
    }

    @Test
    void testEmptyFile() throws IOException {
        String path = "adapter.txt";
        List<String> input = List.of();
        when(reader.loadLines(path)).thenReturn(input);
        List<Order> orders = List.of();
        List<Order> result = adapter.parse(path);
        Assertions.assertEquals(orders, result);
    }

    @Test
    void testCorruptedFile() throws IOException {
        String path = "adapter.txt";
        List<String> input = List.of(
                "2021-02-09T16:00:22|Industrial",
                "2021-02-09T08:42:59|Power Engineer|17480",
                "2021-02-09T10:48:34|Mosque|33120"
        );
        when(reader.loadLines(path)).thenReturn(input);
        Assertions.assertThrows(OrderParseException.class, () -> adapter.parse(path));
    }

    @Test
    void testOrderParseException() {
        String path = "adapter.txt";
        Assertions.assertThrows(OrderParseException.class, () -> adapter.parse(path));
    }
}
