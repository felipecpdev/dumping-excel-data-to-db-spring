package com.felipecpdev.dumpingexceldatatodbspring.services;

import com.felipecpdev.dumpingexceldatatodbspring.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    void save(MultipartFile file);

    List<Product> getAllProducts();
}
