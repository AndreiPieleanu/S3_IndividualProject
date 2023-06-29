package fontys.s3.andreipieleanu.datalayer;

import fontys.s3.andreipieleanu.datalayer.entities.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderStatusDal extends JpaRepository<OrderStatusEntity, Integer> {
}
