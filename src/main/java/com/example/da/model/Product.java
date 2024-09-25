package com.example.da.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Product")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvField(columnName = "id", required = true)
    private Long id;

    @CsvField(columnName = "Product Name", required = true)
    private String name;

    @CsvField(columnName = "Price", required = true)
    private Double price;

    // Getters and Setters
}
