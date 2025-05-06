package mirea.pracs.productcrud.dto.product;

import java.math.BigDecimal;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductPostDto {

  private Integer quantityInStock;
  private String name;
  private String description;
  private MultipartFile image;
  private BigDecimal price;
  private Long productTypeId;

}
