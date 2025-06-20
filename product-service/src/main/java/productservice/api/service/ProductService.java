package productservice.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import productservice.api.dto.ProductRequest;
import productservice.api.dto.ProductResponse;
import productservice.api.model.Product;
import productservice.api.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .skuCode(productRequest.skuCode())
                .price(productRequest.price())
                .imageSrc(productRequest.imageSrc())
                .category(productRequest.category())
                .build();
        productRepository.save(product);
        log.info("Товар успешно создан");
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getSkuCode(), product.getPrice(), product.getImageSrc(), product.getCategory());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getSkuCode(), product.getPrice(), product.getImageSrc(), product.getCategory()))
                .toList();
    }

    public List<ProductResponse> getAllProductsByQuery(String query) {
        return productRepository.findByNameContainsIgnoreCase(query)
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getSkuCode(), product.getPrice(), product.getImageSrc(), product.getCategory()))
                .toList();
    }

    public List<ProductResponse> getAllProductsByCategory(String category) {
        return productRepository.findByCategoryContainsIgnoreCase(category)
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getSkuCode(), product.getPrice(), product.getImageSrc(), product.getCategory()))
                .toList();
    }
}
