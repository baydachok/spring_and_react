package mirea.pracs.productcrud.dto.product;

import java.math.BigDecimal;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductPatchDto {

  private BigDecimal price;
  private Integer quantityInStock;
  private String description;
  private MultipartFile image;
  private String name;

}
