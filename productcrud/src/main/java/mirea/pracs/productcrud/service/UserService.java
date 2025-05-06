package mirea.pracs.productcrud.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import mirea.pracs.productcrud.dto.auth.SignUpRequestDto;
import mirea.pracs.productcrud.entity.User;
import mirea.pracs.productcrud.entity.enums.UserRole;
import mirea.pracs.productcrud.exceptions.ForbiddenException;
import mirea.pracs.productcrud.exceptions.InternalServerErrorException;
import mirea.pracs.productcrud.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Slf4j
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public List<User> findByUsername(String username){
    return userRepository.findByUsername(username);
  }

  public User findOneByAuthentication(Authentication authentication) {
    if (Objects.isNull(authentication)) {
      throw new ForbiddenException("User has not authorized");
    }
    String username = authentication.getName();
    log.info("Username is '{}'", username);
    var users = userRepository.findByUsername(username);
    if (users.isEmpty()) {
      throw new ForbiddenException("User with specified username not found");
    }
    if (users.size() > 1) {
      throw new InternalServerErrorException("Several users with specified username");
    }
    return users.getFirst();
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<User> users = findByUsername(username);
    if (users.isEmpty()) {
      throw new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username));
    } else {
      User user = users.getFirst();
      Collection<String> userRoles = List.of(user.getRole().toString());
      List<GrantedAuthority> grantedAuthoritiesRoles = userRoles.stream()
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());
      return new org.springframework.security.core.userdetails.User(
              user.getUsername(),
              user.getPassword(),
              grantedAuthoritiesRoles
      );
    }

  }

  @Transactional
  public User createNewUser(SignUpRequestDto signUpRequestDto) {
    User user = new User();
    user.setUsername(signUpRequestDto.getUsername());
    user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
    user.setRole(UserRole.USER);
    userRepository.save(user);
    return user;
  }
}
