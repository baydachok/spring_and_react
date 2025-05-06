package mirea.pracs.productcrud.repository;

import mirea.pracs.productcrud.entity.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRecordRepository extends JpaRepository<OrderRecord, Long> {
}
