package mirea.pracs.productcrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import mirea.pracs.productcrud.entity.enums.OrderStatus;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "shop_order")
@Getter
@Setter
@Accessors(chain = true)
public class Order {

  @Id
  @Column(name = "shop_order_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;
  @Column
  private String address;
  @Column
  @UpdateTimestamp
  private LocalDateTime updatedTimestamp;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @Column
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;
  @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
  private List<OrderRecord> orderRecords;

}
