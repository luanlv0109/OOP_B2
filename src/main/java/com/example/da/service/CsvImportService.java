package com.example.da.service;

import com.example.da.model.CsvField;
import com.example.da.model.Product;
import com.example.da.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvImportService {

    @Autowired
    private ProductRepository productRepository;

    public <T> void importCsv(MultipartFile file, Class<T> clazz) throws IOException, IllegalAccessException, InstantiationException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String headerLine = reader.readLine(); // Get header line to map column names
        String[] headers = headerLine.split(",");

        List<T> records = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");

            T instance = clazz.newInstance(); // Create an instance of the class

            for (Field field : clazz.getDeclaredFields()) {
                CsvField annotation = field.getAnnotation(CsvField.class);
                if (annotation != null) {
                    String columnName = annotation.columnName();
                    int index = getColumnIndex(headers, columnName); // Get the index of the column in the CSV file
                    if (index != -1 && data.length > index) {
                        field.setAccessible(true); // Allow setting private fields
                        Object value = convertValue(field.getType(), data[index]); // Convert the value
                        field.set(instance, value);
                    }
                }
            }

            records.add(instance); // Add the populated instance to the list
        }
        saveRecords(records, clazz);
    }

    private Object convertValue(Class<?> type, String data) {
        if (type == Double.class) {
            return Double.parseDouble(data);
        } else if (type == String.class) {
            return data;
        }
        // Add other types as necessary (int, boolean, etc.)
        return null;
    }

    private int getColumnIndex(String[] headers, String columnName) {
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;
    }

    private <T> void saveRecords(List<T> records, Class<T> clazz) {
        if (clazz == Product.class) {
            productRepository.saveAll((List<Product>) records);
        }
        // Handle saving other types of entities here
    }
}