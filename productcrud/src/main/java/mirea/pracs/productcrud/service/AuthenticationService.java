package mirea.pracs.productcrud.service;

import jakarta.transaction.Transactional;
import java.util.List;
import mirea.pracs.productcrud.configuration.JwtTokenUtils;
import mirea.pracs.productcrud.converter.UserConverter;
import mirea.pracs.productcrud.dto.auth.SignInRequestDto;
import mirea.pracs.productcrud.dto.auth.SignInResponseDto;
import mirea.pracs.productcrud.dto.auth.SignUpRequestDto;
import mirea.pracs.productcrud.dto.user.UserGetDto;
import mirea.pracs.productcrud.entity.User;
import mirea.pracs.productcrud.exceptions.ForbiddenException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationService {
  private final UserService userService;
  private final JwtTokenUtils jwtTokenUtils;
  private final AuthenticationManager authenticationManager;
  private final UserConverter userConverter;

  public AuthenticationService(
      UserService userService,
      JwtTokenUtils jwtTokenUtils,
      AuthenticationManager authenticationManager,
      UserConverter userConverter
  ) {
    this.userService = userService;
    this.jwtTokenUtils = jwtTokenUtils;
    this.authenticationManager = authenticationManager;
    this.userConverter = userConverter;
  }

  public ResponseEntity<SignInResponseDto> createAuthToken(SignInRequestDto signInRequestDto) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(signInRequestDto.getUsername(), signInRequestDto.getPassword())
      );
    } catch (BadCredentialsException e) {
      throw new ForbiddenException("Incorrect login or password");
    }
    UserDetails userDetails = userService.loadUserByUsername(signInRequestDto.getUsername());
    String userRole = userDetails.getAuthorities().toString();
    String accessToken = jwtTokenUtils.generateAccessToken(userDetails);
    String refreshToken = jwtTokenUtils.generateRefreshToken(userDetails);

    return ResponseEntity
        .ok(new SignInResponseDto(accessToken, refreshToken, userRole));
  }

  @Transactional
  public ResponseEntity<UserGetDto> createNewUser(SignUpRequestDto signUpRequestDto) {
    if (!validateSignUpRequest(signUpRequestDto)) {
      throw new ForbiddenException("Invalid data");
    }
    User user = userService.createNewUser(signUpRequestDto);
    var userGetDto = userConverter.convertToUserGetDto(user);
    return ResponseEntity.ok(userGetDto);
  }

  private boolean validateSignUpRequest(SignUpRequestDto signUpRequestDto) {
    List<User> usersWithSameUsername = userService.findByUsername(signUpRequestDto.getUsername());
    if (!usersWithSameUsername.isEmpty()) {
      throw new ForbiddenException("A user with the specified username already exists");
    }
    return true;
  }
}
