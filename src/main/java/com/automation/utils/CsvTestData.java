package com.automation.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class CsvTestData {

    private static final AtomicInteger UNIQUE_SEQUENCE = new AtomicInteger();

    private CsvTestData() {
    }

    public static Object[][] load(String classpathFile, String... requiredHeaders) {
        if (requiredHeaders.length == 0) {
            throw new IllegalArgumentException("requiredHeaders must name every column");
        }
        InputStream input = CsvTestData.class.getClassLoader().getResourceAsStream(classpathFile);
        if (input == null) {
            throw new IllegalStateException("Test data file not found: " + classpathFile);
        }
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();
        try (CSVParser parser = CSVParser.parse(new InputStreamReader(input, StandardCharsets.UTF_8), format)) {
            List<String> headers = parser.getHeaderNames();
            if (!headers.equals(Arrays.asList(requiredHeaders))) {
                throw new IllegalStateException("Expected columns " + Arrays.toString(requiredHeaders)
                        + " in " + classpathFile + " but found " + headers);
            }
            List<Object[]> rows = new ArrayList<>();
            for (CSVRecord record : parser) {
                Object[] values = new Object[requiredHeaders.length];
                for (int i = 0; i < requiredHeaders.length; i++) {
                    values[i] = resolve(record.get(requiredHeaders[i]));
                }
                rows.add(values);
            }
            if (rows.isEmpty()) {
                throw new IllegalStateException("Test data file has no data rows: " + classpathFile);
            }
            return rows.toArray(new Object[0][]);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read test data file: " + classpathFile, e);
        }
    }

    private static String resolve(String value) {
        if (value.contains("${unique}")) {
            String suffix = UNIQUE_SEQUENCE.incrementAndGet() + Long.toString(System.nanoTime() % 10000000);
            return value.replace("${unique}", suffix);
        }
        return value;
    }
}
