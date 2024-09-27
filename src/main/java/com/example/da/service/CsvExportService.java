package com.example.da.service;

import com.example.da.model.Product;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface CsvExportService {
    <T> void exportCsv(HttpServletResponse response, Class<Product> clazz) throws IOException, IllegalAccessException;

}
