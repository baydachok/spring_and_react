package mirea.pracs.productcrud.dto.auth;

import lombok.Data;

@Data
public class SignInRequestDto {
  private String username;
  private String password;
}
