package org.example.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileResultWriter implements Writer {
    @Override
    public void write(Map<String, Double> result) {
        List<String> ordersToFile = new ArrayList<>();
        for (Map.Entry<String, Double> entry : result.entrySet()) {
            String line = entry.getKey() + " - " + entry.getValue();
            ordersToFile.add(line);
        }
        try {
            Files.write(Path.of("result.txt"), ordersToFile);
            System.out.println("File was successfully made.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
