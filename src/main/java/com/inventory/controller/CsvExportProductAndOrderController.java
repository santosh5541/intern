package com.inventory.controller;

import com.inventory.csv.CsvExportProductAndOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/csv")
public class CsvExportProductAndOrderController {


    @Autowired
    private CsvExportProductAndOrderService csvExportService;


    @GetMapping("/export-products")
    public ResponseEntity<String> exportProductsCsv(@RequestParam String filePath) {
        try {
            csvExportService.exportProductsToCsv(filePath);
            return ResponseEntity.ok("CSV export successful");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CSV export failed");
        }
    }


    @GetMapping("/export-orders")
    public ResponseEntity<String> exportOrdersCsv(@RequestParam String filePath) {
        try {
            csvExportService.exportOrdersToCsv(filePath);
            return ResponseEntity.ok("CSV export successful");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CSV export failed");
        }
    }

//http://localhost:8080/csv/export-products/?filePath=/home/santosh/Desktop/product.csv

}
