package mirea.pracs.productcrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import mirea.pracs.productcrud.entity.enums.UserRole;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "user_account")
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  @Column
  private String username;
  @Column
  private String password;
  @Column
  @Enumerated(EnumType.STRING)
  private UserRole role;
  @Column
  @UpdateTimestamp
  private LocalDateTime updatedTimestamp;
  @OneToMany(mappedBy = "user")
  private List<Order> orders;

}
