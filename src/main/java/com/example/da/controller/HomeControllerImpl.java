package com.example.da.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControllerImpl implements HomeController {

    @GetMapping("/")
    public String home() {
        return "csv"; // Trả về file index.jsp trong thư mục WEB-INF/views/
    }
}
