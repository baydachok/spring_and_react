package mirea.pracs.productcrud.dto.gpt;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GptCompletionDto {

  private String modelUri;
  private GptCompletionOptionsDto completionOptions;
  private List<GptMessageDto> messages;

}
