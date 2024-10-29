package inventoryservice.api.inventory_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "inventory", schema = "public", catalog = "inventory-service")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String skuCode;
    private Integer quantity;
}
