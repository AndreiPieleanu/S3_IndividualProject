package fontys.s3.andreipieleanu.domain;

public enum OrderStatusEnum {
    NEW(1),
    PENDING(2),
    SHIPPED(3),
    DELIVERED(4),
    CLOSED(5);
    private final int index;
    OrderStatusEnum(int index){
        this.index = index;
    }
    public int getValue(){
        return index;
    }
    public static OrderStatusEnum getStatus(int index){
        return switch (index) {
            case 1 -> NEW;
            case 2 -> PENDING;
            case 3 -> SHIPPED;
            case 4 -> DELIVERED;
            default -> CLOSED;
        };
    }
}

