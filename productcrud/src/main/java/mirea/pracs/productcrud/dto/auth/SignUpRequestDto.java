package mirea.pracs.productcrud.dto.auth;

import lombok.Data;

@Data
public class SignUpRequestDto {
  private String username;
  private String password;

  public SignUpRequestDto(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
