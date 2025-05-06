package mirea.pracs.productcrud.dto.orderrecord;

import lombok.Data;

@Data
public class OrderRecordPostDto {

  private Long productId;
  private Integer quantity;

}
