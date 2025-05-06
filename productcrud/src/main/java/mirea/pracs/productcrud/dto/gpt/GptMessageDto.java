package mirea.pracs.productcrud.dto.gpt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GptMessageDto {

  private String role;
  private String text;

}
