package org.example.writer;

import java.util.Map;

public interface ResultWriter {
    void write(Map<String, Double> result, String pathResult);
}
