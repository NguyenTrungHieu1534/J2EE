package com.example.demo.service;

import com.example.demo.model.Product;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

    List<Product> listProduct = new ArrayList<>();

    public List<Product> getAll() {
        return listProduct;
    }

    public Product get(int id) {
        return listProduct.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(Product newProduct) {
        int maxId = listProduct.stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0);

        newProduct.setId(maxId + 1);
        listProduct.add(newProduct);
    }

    public void update(Product editProduct) {
        Product find = get(editProduct.getId());
        if (find != null) {
            find.setPrice(editProduct.getPrice());
            find.setName(editProduct.getName());
            find.setCategory(editProduct.getCategory());
            if (editProduct.getImage() != null) {
                find.setImage(editProduct.getImage());
            }
        }
    }

    // =========================
    // updateImage method
    // =========================
    public void updateImage(Product newProduct, MultipartFile imageProduct) {

        String contentType = imageProduct.getContentType();
        if (contentType == null) {
            throw new IllegalArgumentException("Tệp tải lên không phải là hình ảnh!");
        }

        if (!imageProduct.isEmpty()) {
            try {
                Path dirImages = Paths.get("static/images");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }

                String newFileName = UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);

                Files.copy(
                        imageProduct.getInputStream(),
                        pathFileUpload,
                        StandardCopyOption.REPLACE_EXISTING
                );

                newProduct.setImage(newFileName);

            } catch (IOException e) {
                throw new RuntimeException("Không thể lưu hình ảnh sản phẩm", e);
            }
        }
    }
}