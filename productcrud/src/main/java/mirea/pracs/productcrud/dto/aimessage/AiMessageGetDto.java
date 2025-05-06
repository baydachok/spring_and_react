package mirea.pracs.productcrud.dto.aimessage;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiMessageGetDto {

  private Long aiMessageId;
  private LocalDateTime createdTimestamp;
  private String role;
  private String message;

}
