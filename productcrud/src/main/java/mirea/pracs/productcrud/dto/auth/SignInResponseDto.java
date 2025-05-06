package mirea.pracs.productcrud.dto.auth;

import lombok.Data;

@Data
public class SignInResponseDto {
  private String accessToken;

  private String refreshToken;

  private String role;

  public SignInResponseDto(String accessToken, String refreshToken, String role) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.role = role;
  }

}
