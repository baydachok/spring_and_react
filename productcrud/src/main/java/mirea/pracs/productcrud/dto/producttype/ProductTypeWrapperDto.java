package mirea.pracs.productcrud.dto.producttype;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductTypeWrapperDto {

  List<ProductTypeGetDto> data;

}
