package com.example.da.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CsvController {
    String showCsvPage();

    ResponseEntity<String> importCsv(MultipartFile file);

    void exportCsv(HttpServletResponse response) throws IOException, IllegalAccessException;
}
