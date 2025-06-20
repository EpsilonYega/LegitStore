package productservice.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import productservice.api.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainsIgnoreCase(String name);

    List<Product> findByCategoryContainsIgnoreCase(String category);
}
