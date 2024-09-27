package com.example.da.controller;

import com.example.da.model.Product;
import com.example.da.service.CsvExportService;
import com.example.da.service.CsvImportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/api/csv")
public class CsvControllerImpl implements CsvController {

    @Autowired
    private CsvImportService csvImportService;

    @Autowired
    private CsvExportService csvExportService;

    // Method to render the CSV import/export page
    @GetMapping("/view")
    public String showCsvPage() {
        return "csv"; // Trả về file JSP tên csv.jsp
    }

    @PostMapping("/import")
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
        try {
            csvImportService.importCsv(file, Product.class);
            return ResponseEntity.ok("File imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("File import failed");
        }
    }

    @GetMapping("/export")
    public void exportCsv(HttpServletResponse response) throws IOException, IllegalAccessException {
        csvExportService.exportCsv(response, Product.class); // Specify the class type here
    }
}
