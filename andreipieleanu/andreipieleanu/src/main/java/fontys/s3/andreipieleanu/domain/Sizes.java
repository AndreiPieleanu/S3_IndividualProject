package fontys.s3.andreipieleanu.domain;

public enum Sizes {
    XXS(1),
    XS(2),
    S(3),
    M(4),
    L(5),
    XL(6),
    XXL(7);
    private final int index;
    Sizes(int index){
        this.index = index;
    }
    public int getValue(){
        return index;
    }
    public static Sizes getSize(int index){
        return switch (index) {
            case 1 -> XXS;
            case 2 -> XS;
            case 3 -> S;
            case 4 -> M;
            case 5 -> L;
            case 6 -> XL;
            default -> XXL;
        };
    }
}
