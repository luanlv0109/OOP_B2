package com.example.da.service;

import com.example.da.model.Product;
import com.example.da.repository.ProductRepository;
import com.example.da.utils.CsvUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CsvExportServiceImpl implements CsvExportService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public <T> void exportCsv(HttpServletResponse response, Class<Product> clazz) throws IOException, IllegalAccessException {
        List<Product> records = productRepository.findAll();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment;filename=products.csv");

        StringBuilder csvContent = CsvUtil.buildCsvContent(records, clazz);
        response.getWriter().write(csvContent.toString());
    }
}
