package com.example.da.service;

import com.example.da.model.CsvField;
import com.example.da.model.Product;
import com.example.da.repository.ProductRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

@Service
public class CsvExportService {

    @Autowired
    private ProductRepository productRepository;

    public <T> void exportCsv(HttpServletResponse response, Class<Product> clazz) throws IOException, IllegalAccessException {
        List<T> records = (List<T>) productRepository.findAll(); // Example: for Product

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment;filename=products.csv");

        StringBuilder csvBuilder = new StringBuilder();
        for (Field field : clazz.getDeclaredFields()) {
            CsvField annotation = field.getAnnotation(CsvField.class);
            if (annotation != null) {
                csvBuilder.append(annotation.columnName()).append(",");
            }
        }
        csvBuilder.append("\n");

        for (T record : records) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(record);
                csvBuilder.append(value).append(",");
            }
            csvBuilder.append("\n");
        }

        response.getWriter().write(csvBuilder.toString());
    }
}
