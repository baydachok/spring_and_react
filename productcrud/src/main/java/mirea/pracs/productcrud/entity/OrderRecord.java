package mirea.pracs.productcrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "order_record")
@Getter
@Setter
@Accessors(chain = true)
public class OrderRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderRecordId;
  @Column
  private Integer quantity;
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

}
