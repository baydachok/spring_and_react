package mirea.pracs.productcrud.dto.order;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import mirea.pracs.productcrud.dto.orderrecord.OrderRecordGetDto;

@Data
@Builder
public class OrderGetDto {

  private Long orderId;
  private String address;
  private LocalDateTime updatedTimestamp;
  private String orderStatus;
  private List<OrderRecordGetDto> orderRecords;

}
