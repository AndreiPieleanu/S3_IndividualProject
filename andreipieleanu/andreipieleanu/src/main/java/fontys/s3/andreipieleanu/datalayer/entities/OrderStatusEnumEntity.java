package fontys.s3.andreipieleanu.datalayer.entities;

public enum OrderStatusEnumEntity {
    NEW(1),
    PENDING(2),
    SHIPPED(3),
    DELIVERED(4),
    CLOSED(5);
    private final int index;
    OrderStatusEnumEntity(int index){
        this.index = index;
    }
    public int getValue(){
        return index;
    }
    public static OrderStatusEnumEntity getStatus(int index){
        return switch (index) {
            case 1 -> NEW;
            case 2 -> PENDING;
            case 3 -> SHIPPED;
            case 4 -> DELIVERED;
            default -> CLOSED;
        };
    }
}
