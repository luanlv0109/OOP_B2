package com.example.da.service;

import com.example.da.model.CsvField;
import com.example.da.model.Product;
import com.example.da.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.BOMInputStream;
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

public interface CsvImportService {
   <T> void importCsv(MultipartFile file, Class<T> clazz) throws IOException, InstantiationException, IllegalAccessException;
}