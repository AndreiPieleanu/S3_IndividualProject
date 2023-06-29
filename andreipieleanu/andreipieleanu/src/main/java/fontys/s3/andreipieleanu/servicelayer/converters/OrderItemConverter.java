package fontys.s3.andreipieleanu.servicelayer.converters;

import fontys.s3.andreipieleanu.datalayer.entities.OrderItemEntity;
import fontys.s3.andreipieleanu.domain.OrderItem;

public final class OrderItemConverter {
    private OrderItemConverter(){}
    public static OrderItem convert(OrderItemEntity entity){
        return OrderItem
                .builder()
                .id(entity.getId())
                .item(ClothesConverter.convert(entity.getItem()))
                .amount(entity.getAmount())
                .price(entity.getPrice())
                .build();
    }
    public static OrderItemEntity convert(OrderItem item){
        return OrderItemEntity
                .builder()
                .id(item.getId())
                .item(ClothesConverter.convert(item.getItem()))
                .amount(item.getAmount())
                .price(item.getPrice())
                .build();
    }
}
