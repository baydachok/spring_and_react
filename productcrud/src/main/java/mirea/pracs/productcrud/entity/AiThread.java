package mirea.pracs.productcrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "ai_thread")
@Getter
@Setter
@Accessors(chain = true)
public class AiThread {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long aiThreadId;
  @Column
  @CreationTimestamp
  private LocalDateTime createdTimestamp;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @OneToMany(mappedBy = "aiThread", fetch = FetchType.EAGER)
  private List<AiMessage> aiMessages;

}
