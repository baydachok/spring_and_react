package mirea.pracs.productcrud.dto.aithread;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AiThreadWrapperDto {

  private List<AiThreadGetDto> data;

}
