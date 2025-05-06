package mirea.pracs.productcrud.dto.orderrecord;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRecordGetDto {

  private Long productId;
  private Integer quantity;

}
