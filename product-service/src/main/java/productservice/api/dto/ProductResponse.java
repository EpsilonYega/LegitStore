package productservice.api.dto;

import java.math.BigDecimal;

public record ProductResponse(long id, String name, String description, String skuCode, BigDecimal price, String imageSrc, String category) {
}
