package mirea.pracs.productcrud.dto.product;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductWrapperDto {

  private List<ProductGetDto> data;

}
