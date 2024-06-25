package org.example.finalproject33.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @GetMapping
    public List<String> getCategories() {
        return Arrays.asList("Electronics", "Books", "Clothing", "Home", "Toys");
    }
}
