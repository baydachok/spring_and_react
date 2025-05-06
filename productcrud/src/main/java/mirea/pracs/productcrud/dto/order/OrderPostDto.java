package mirea.pracs.productcrud.dto.order;

import java.util.List;
import lombok.Data;
import mirea.pracs.productcrud.dto.orderrecord.OrderRecordPostDto;

@Data
public class OrderPostDto {

  private String address;
  private List<OrderRecordPostDto> orderRecords;

}
