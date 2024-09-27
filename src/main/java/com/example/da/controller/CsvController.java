package com.example.da.controller;

import com.example.da.model.Product;
import com.example.da.service.CsvExportService;
import com.example.da.service.CsvImportService;
import com.example.da.service.ManagerEntityService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/csv")
public class CsvController {

    @Autowired
    private CsvImportService csvImportService;

    @Autowired
    private CsvExportService csvExportService;

    @Autowired
    ManagerEntityService managerEntityService;

    @GetMapping("/view")
    public String showCsvPage() {
        return "csv";
    }

    @PostMapping("/import")
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file,
                                            @RequestParam("selectedEntity") String selectedEntity) {
        try {
            String packageName = "com.example.da.model.";
            Class<?> entityClass = Class.forName(packageName + selectedEntity);

                csvImportService.importCsv(file,entityClass);
            return ResponseEntity.ok("File imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("File import failed");
        }
    }

    @GetMapping("/export")
    public void exportCsv(HttpServletResponse response) throws IOException, IllegalAccessException {
        csvExportService.exportCsv(response, Product.class); // Specify the class type here
    }

    @GetMapping("/get-list-entity")
    @ResponseBody
    public List<String> getEntityNames() {
        return managerEntityService.getEntityNames("com.example.da.model");
    }

}
