package com.example.da.utils;

import com.example.da.model.CsvField;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    public static <T> StringBuilder buildCsvContent(List<T> records, Class<T> clazz) throws IllegalAccessException {
        StringBuilder csvBuilder = new StringBuilder();
        appendHeaders(csvBuilder, clazz);
        appendRecords(csvBuilder, records, clazz);
        return csvBuilder;
    }

    private static <T> void appendHeaders(StringBuilder csvBuilder, Class<T> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            CsvField annotation = field.getAnnotation(CsvField.class);
            if (annotation != null) {
                csvBuilder.append(annotation.columnName()).append(",");
            }
        }
        csvBuilder.setLength(csvBuilder.length() - 1);
        csvBuilder.append("\n");
    }

    private static <T> void appendRecords(StringBuilder csvBuilder, List<T> records, Class<T> clazz) throws IllegalAccessException {
        for (T record : records) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(record);
                csvBuilder.append(value).append(",");
            }
            csvBuilder.setLength(csvBuilder.length() - 1);
            csvBuilder.append("\n");
        }
    }

    public static <T> List<T> importCsv(BufferedReader reader, Class<T> clazz) throws IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String headerLine = reader.readLine();
        String[] headers = headerLine.split(",");

        List<T> records = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");

            T instance = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                CsvField annotation = field.getAnnotation(CsvField.class);
                if (annotation != null) {
                    String columnName = annotation.columnName();
                    int index = getColumnIndex(headers, columnName);
                    if (index != -1 && data.length > index) {
                        field.setAccessible(true);
                        Object value = convertValue(field.getType(), data[index]);
                        field.set(instance, value);
                    }
                }
            }

            records.add(instance);
        }
        return records;
    }

    private static Object convertValue(Class<?> type, String data) {
        if (type == Double.class) {
            return Double.parseDouble(data);
        } else if (type == String.class) {
            return data;
        }
        return null;
    }

    private static int getColumnIndex(String[] headers, String columnName) {
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;
    }
}
