package productservice.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import productservice.api.dto.ProductRequest;
import productservice.api.dto.ProductResponse;
import productservice.api.model.Product;
import productservice.api.service.ProductService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/search/{query}")
    public List<ProductResponse> getAllProductsByQuery(@PathVariable("query") String query) {
        return productService.getAllProductsByQuery(query);
    }

    @GetMapping("/category/{category}")
    public List<ProductResponse> getAllProductsByCategory(@PathVariable("category") String category) {
        return productService.getAllProductsByCategory(category);
    }
}
