package mirea.pracs.productcrud.converter;

import mirea.pracs.productcrud.dto.user.UserGetDto;
import mirea.pracs.productcrud.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

  public UserGetDto convertToUserGetDto(User user) {
    return UserGetDto.builder()
        .userId(user.getUserId())
        .username(user.getUsername())
        .build();
  }

}
