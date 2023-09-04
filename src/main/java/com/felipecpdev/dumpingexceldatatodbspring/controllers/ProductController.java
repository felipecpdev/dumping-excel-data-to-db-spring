package com.felipecpdev.dumpingexceldatatodbspring.controllers;

import com.felipecpdev.dumpingexceldatatodbspring.entity.Product;
import com.felipecpdev.dumpingexceldatatodbspring.services.ProductService;
import com.felipecpdev.dumpingexceldatatodbspring.xls.FileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (FileReader.checkExcelFormat(file)) {
            this.productService.save(file);
            return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to db"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
    }

    @GetMapping
    public List<Product> getAllProduct() {
        return this.productService.getAllProducts();
    }
}
