package mirea.pracs.productcrud.repository;

import java.util.List;
import mirea.pracs.productcrud.entity.AiThread;
import mirea.pracs.productcrud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiThreadRepository extends JpaRepository<AiThread, Long> {

  List<AiThread> findAllByUser(User user);

}
