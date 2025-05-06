package mirea.pracs.productcrud.dto.aithread;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import mirea.pracs.productcrud.dto.aimessage.AiMessageGetDto;

@Data
@Builder
public class AiThreadGetDto {

  private Long aiThreadId;
  private LocalDateTime createdTimestamp;
  private List<AiMessageGetDto> aiMessages;

}
