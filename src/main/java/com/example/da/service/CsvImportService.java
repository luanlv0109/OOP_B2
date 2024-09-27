package com.example.da.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface CsvImportService {
    public <T> void importCsv(MultipartFile file, Class<T> clazz) throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException;
}
