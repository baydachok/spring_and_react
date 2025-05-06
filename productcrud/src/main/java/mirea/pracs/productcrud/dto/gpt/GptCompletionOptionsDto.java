package mirea.pracs.productcrud.dto.gpt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GptCompletionOptionsDto {

  private boolean stream;
  private double temperature;
  private String maxTokens;

}
