package mirea.pracs.productcrud.dto.producttype;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductTypeGetDto {

  private Long productTypeId;
  private LocalDateTime updatedTimestamp;
  private String name;

}
