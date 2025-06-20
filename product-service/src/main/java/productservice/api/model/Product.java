package productservice.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "product", schema = "public", catalog = "product-service")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String skuCode;
    @Column
    private BigDecimal price;
    @Column
    private String imageSrc;
    @Column
    private String category;
}