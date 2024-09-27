package com.example.da.service;

import com.example.da.model.Product;
import com.example.da.repository.ProductRepository;
import com.example.da.utils.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class CsvImportServiceImpl implements CsvImportService {

    @Autowired
    private ProductRepository productRepository;

    public <T> void importCsv(MultipartFile file, Class<T> clazz) throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        List<T> records = CsvUtil.importCsv(reader, clazz);
        saveRecords(records, clazz);
    }

    private <T> void saveRecords(List<T> records, Class<T> clazz) {
        if (clazz == Product.class) {
            productRepository.saveAll((List<Product>) records);
        }
    }
}
