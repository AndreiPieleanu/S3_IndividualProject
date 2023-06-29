package fontys.s3.andreipieleanu.datalayer;

import fontys.s3.andreipieleanu.datalayer.entities.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderItemDal extends JpaRepository<OrderItemEntity, Integer> {
}
