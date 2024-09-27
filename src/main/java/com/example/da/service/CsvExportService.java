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
public interface CsvExportService {
    <T> void exportCsv(HttpServletResponse response, Class<Product> clazz) throws IOException, IllegalAccessException;
}
