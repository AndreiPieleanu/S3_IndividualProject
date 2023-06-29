package fontys.s3.andreipieleanu.servicelayer.converters;

import fontys.s3.andreipieleanu.datalayer.entities.OrderStatusEntity;
import fontys.s3.andreipieleanu.datalayer.entities.OrderStatusEnumEntity;
import fontys.s3.andreipieleanu.domain.OrderStatus;
import fontys.s3.andreipieleanu.domain.OrderStatusEnum;

public final class OrderStatusConverter {
    private OrderStatusConverter(){}
    public static OrderStatus convert(OrderStatusEntity entity){
        return OrderStatus
                .builder()
                .id(entity.getId())
                .orderStatusEnum(OrderStatusEnum.getStatus(entity.getStatusEnum().getValue()))
                .build();
    }
    public static OrderStatusEntity convert(OrderStatus orderStatus){
        return OrderStatusEntity
                .builder()
                .id(orderStatus.getId())
                .statusEnum(OrderStatusEnumEntity.getStatus(orderStatus.getOrderStatusEnum().getValue()))
                .build();
    }
}
