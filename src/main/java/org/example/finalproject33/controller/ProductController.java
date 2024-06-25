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

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

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
                product.setImage(image.getBytes());
            } else {
                // 기본 이미지를 설정합니다.
                byte[] defaultImage = getDefaultImage();
                product.setImage(defaultImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    private byte[] getDefaultImage() {
        // 기본 이미지 파일을 바이트 배열로 읽어옵니다.
        try {
            InputStream is = getClass().getResourceAsStream("/static/images/no-image.png");
            if (is != null) {
                return is.readAllBytes();
            } else {
                // 기본 이미지 파일을 찾을 수 없을 때 예외를 던집니다.
                throw new IOException("기본 이미지가 없습니다");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
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
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
