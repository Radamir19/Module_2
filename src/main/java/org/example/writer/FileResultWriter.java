package org.example.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileResultWriter implements ResultWriter {
    @Override
    public void write(Map<String, Double> result, String pathResult) throws IOException{
        List<String> ordersToFile = new ArrayList<>();
        for (Map.Entry<String, Double> entry : result.entrySet()) {
            String line = entry.getKey() + " - " + entry.getValue();
            ordersToFile.add(line);
        }
        Files.write(Path.of(pathResult), ordersToFile);
    }
}
