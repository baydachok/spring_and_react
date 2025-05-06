package mirea.pracs.productcrud.repository;

import java.util.List;
import mirea.pracs.productcrud.entity.Order;
import mirea.pracs.productcrud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findAllByUser(User user);
}
