package productservice.api.dto;

import java.math.BigDecimal;

public record ProductRequest(long id, String name, String description, String skuCode, BigDecimal price) {
}
