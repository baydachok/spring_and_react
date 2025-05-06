package mirea.pracs.productcrud.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserGetDto {

  private Long userId;
  private String username;

}
