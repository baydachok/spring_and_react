package mirea.pracs.productcrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table
@Data
public class ProductType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productTypeId;
  @Column
  private String name;
  @Column
  @UpdateTimestamp
  private LocalDateTime updatedTimestamp;

}
