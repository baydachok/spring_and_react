package mirea.pracs.productcrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import mirea.pracs.productcrud.entity.enums.AiMessageRole;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "ai_message")
@Getter
@Setter
@Accessors(chain = true)
public class AiMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long aiMessageId;
  @Column
  @Enumerated(EnumType.STRING)
  private AiMessageRole role;
  @Column
  private String message;
  @Column
  @CreationTimestamp
  private LocalDateTime createdTimestamp;
  @ManyToOne
  @JoinColumn(name = "ai_thread_id")
  private AiThread aiThread;

}
