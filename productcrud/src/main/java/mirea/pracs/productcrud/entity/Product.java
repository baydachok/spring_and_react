package mirea.pracs.productcrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table
@Getter
@Setter
@Accessors(chain = true)
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;
  @Column
  private String name;
  @Column
  private String description;
  @Column
  private String imageSrc;
  @Column
  private BigDecimal price;
  @Column
  private Integer quantityInStock;
  @Column
  @UpdateTimestamp
  private LocalDateTime updatedTimestamp;
  @ManyToOne
  @JoinColumn(name = "product_type_id")
  private ProductType productType;

}
