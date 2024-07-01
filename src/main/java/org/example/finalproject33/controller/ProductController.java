package org.example.finalproject33.controller;

import org.example.finalproject33.model.Product;
import org.example.finalproject33.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestParam("name") String name,
                                              @RequestParam("category") String category,
                                              @RequestParam("price") String price,
                                              @RequestParam("description") String description,
                                              @RequestParam(value = "image", required = false) MultipartFile image) {
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setDescription(description);
        try {
            if (image != null && !image.isEmpty()) {
                String imagePath = saveImage(image);
                product.setImagePath(imagePath);
            } else {
                // 기본 이미지 경로 설정
                String defaultImagePath = "/images/no-image.png";
                product.setImagePath(defaultImagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }


    private String saveImage(MultipartFile image) throws IOException {
        String directory = new File("src/main/resources/static/images/").getAbsolutePath(); // 이미지 저장 경로 설정
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        String filePath = directory + File.separator+fileName;
        File dest = new File(filePath);
        image.transferTo(dest);
        return "/images/" + fileName;
    }

@GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProductsByName(@RequestParam("name") String name, Pageable pageable) {
        Page<Product> products = productService.searchProductsByName(name, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<Page<Product>> searchProductsByCategory(@RequestParam("category") String category, Pageable pageable) {
        Page<Product> products = productService.searchProductsByCategory(category, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

