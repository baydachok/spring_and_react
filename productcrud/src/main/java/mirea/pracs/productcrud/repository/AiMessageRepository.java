package mirea.pracs.productcrud.repository;

import mirea.pracs.productcrud.entity.AiMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiMessageRepository extends JpaRepository<AiMessage, Long> {
}
