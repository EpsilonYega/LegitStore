package productservice.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import productservice.api.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
