package fontys.s3.andreipieleanu.datalayer;

import fontys.s3.andreipieleanu.datalayer.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderDal extends JpaRepository<OrderEntity, Integer> {
    @Query("SELECT o FROM OrderEntity o WHERE " +
            "cast(o.id as string ) LIKE " +
            "CONCAT('%', :orderId, '%')")
    List<OrderEntity> findAllBySimilarOrderId(@Param("orderId") Integer orderId);
    
    @Query("select o from OrderEntity o where o.customer.id=:userId")
    List<OrderEntity> getAllOrdersOfUser(@Param("userId") Integer userId);

    @Modifying
    @Query("update OrderEntity o set o.status.id=:newStatus where o.id=:orderId")
    Integer updateOrderStatus(@Param("newStatus") Integer newStatus,
                              @Param("orderId") Integer orderId);

    @Query("SELECT YEAR(o.createdDate) AS year, MONTH(o.createdDate) AS " +
            "month, SUM(oi.price * oi.amount) AS totalSale " +
            "FROM OrderEntity o " +
            "JOIN o.orderItems oi " +
            "WHERE o.createdDate >= :startDate " +
            "  AND o.createdDate <= :endDate " +
            "GROUP BY YEAR(o.createdDate), MONTH(o.createdDate)")
    List<Object[]> calculateSalesForPastXMonths(@Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);

    @Query("SELECT oi.item, SUM(oi.amount) AS totalAmount " +
            "FROM OrderEntity o " +
            "JOIN o.orderItems oi " +
            "WHERE o.createdDate >= :startDate " +
            "  AND o.createdDate <= :endDate " +
            "GROUP BY oi.item " +
            "ORDER BY totalAmount DESC")
    List<Object[]> getTop5MostSoldProductsForPastXMonths(@Param("startDate") LocalDateTime startDate,
                                                         @Param("endDate") LocalDateTime endDate);
}
