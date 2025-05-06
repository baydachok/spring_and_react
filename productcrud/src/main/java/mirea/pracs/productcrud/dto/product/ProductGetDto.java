package mirea.pracs.productcrud.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import mirea.pracs.productcrud.dto.producttype.ProductTypeGetDto;

@Data
@Builder
public class ProductGetDto {

  private Long productId;
  private BigDecimal price;
  private Integer quantityInStock;
  private LocalDateTime updatedTimestamp;
  private String description;
  private String imageSrc;
  private String name;
  private ProductTypeGetDto productTypeGetDto;

}
