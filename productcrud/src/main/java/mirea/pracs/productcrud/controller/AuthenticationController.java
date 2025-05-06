package mirea.pracs.productcrud.controller;

import mirea.pracs.productcrud.dto.auth.SignInRequestDto;
import mirea.pracs.productcrud.dto.auth.SignInResponseDto;
import mirea.pracs.productcrud.dto.auth.SignUpRequestDto;
import mirea.pracs.productcrud.dto.user.UserGetDto;
import mirea.pracs.productcrud.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<UserGetDto> signUp(@RequestBody SignUpRequestDto request) {
    return authenticationService.createNewUser(request);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<SignInResponseDto> signIn(@RequestBody SignInRequestDto request) {
    return authenticationService.createAuthToken(request);
  }

}
