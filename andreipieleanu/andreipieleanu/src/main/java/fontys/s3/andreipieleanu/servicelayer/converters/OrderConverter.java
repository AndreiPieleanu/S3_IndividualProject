package fontys.s3.andreipieleanu.servicelayer.converters;

import fontys.s3.andreipieleanu.datalayer.entities.OrderEntity;
import fontys.s3.andreipieleanu.datalayer.entities.OrderItemEntity;
import fontys.s3.andreipieleanu.domain.Order;
import fontys.s3.andreipieleanu.domain.OrderItem;

import java.util.HashMap;
import java.util.Map;

public final class OrderConverter {
    private OrderConverter(){}
    public static Order convert(OrderEntity entity){
        Map<Integer, OrderItem> convertedMap = new HashMap<>();
        entity.getOrderItems().forEach((k, v) -> convertedMap.put(k,
                OrderItemConverter.convert(v)));
        return Order
                .builder()
                .id(entity.getId())
                .createdDate(entity.getCreatedDate())
                .customer(UserConverter.convert(entity.getCustomer()))
                .address(AddressConverter.convert(entity.getAddressEntity()))
                .orderItems(convertedMap)
                .status(OrderStatusConverter.convert(entity.getStatus()))
                .build();
    }
    public static OrderEntity convert(Order order){
        Map<Integer, OrderItemEntity> convertedMap = new HashMap<>();
        order.getOrderItems().forEach((k, v) -> convertedMap.put(k,
                OrderItemConverter.convert(v)));
        return OrderEntity
                .builder()
                .id(order.getId())
                .createdDate(order.getCreatedDate())
                .customer(UserConverter.convert(order.getCustomer()))
                .addressEntity(AddressConverter.convert(order.getAddress()))
                .orderItems(convertedMap)
                .status(OrderStatusConverter.convert(order.getStatus()))
                .build();
    }
}
