package com.automation.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ExcelTestData {

    private ExcelTestData() {
    }

    public static Object[][] load(String classpathFile, String... requiredHeaders) {
        if (requiredHeaders.length == 0) {
            throw new IllegalArgumentException("requiredHeaders must name every column");
        }
        InputStream input = ExcelTestData.class.getClassLoader().getResourceAsStream(classpathFile);
        if (input == null) {
            throw new IllegalStateException("Test data file not found: " + classpathFile);
        }
        try (Workbook workbook = WorkbookFactory.create(input)) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalStateException("Test data file has no header row: " + classpathFile);
            }
            List<String> headers = new ArrayList<>();
            for (int i = 0; i < requiredHeaders.length; i++) {
                headers.add(formatter.formatCellValue(headerRow.getCell(i)).trim());
            }
            if (!headers.equals(Arrays.asList(requiredHeaders))) {
                throw new IllegalStateException("Expected columns " + Arrays.toString(requiredHeaders)
                        + " in " + classpathFile + " but found " + headers);
            }
            List<Object[]> rows = new ArrayList<>();
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                Object[] values = new Object[requiredHeaders.length];
                boolean empty = true;
                for (int column = 0; column < requiredHeaders.length; column++) {
                    String value = formatter.formatCellValue(row.getCell(column)).trim();
                    if (!value.isEmpty()) {
                        empty = false;
                    }
                    values[column] = value;
                }
                if (!empty) {
                    rows.add(values);
                }
            }
            if (rows.isEmpty()) {
                throw new IllegalStateException("Test data file has no data rows: " + classpathFile);
            }
            return rows.toArray(new Object[0][]);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read test data file: " + classpathFile, e);
        }
    }
}
