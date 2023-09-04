package com.felipecpdev.dumpingexceldatatodbspring.services.impl;

import com.felipecpdev.dumpingexceldatatodbspring.entity.Product;
import com.felipecpdev.dumpingexceldatatodbspring.repository.ProductRepository;
import com.felipecpdev.dumpingexceldatatodbspring.services.ProductService;
import com.felipecpdev.dumpingexceldatatodbspring.xls.FileReader;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void save(MultipartFile file) {
        try {
            List<Product> products = FileReader.convertExcelToListOfProduct(file.getInputStream());

            this.productRepository.saveAll(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }
}
