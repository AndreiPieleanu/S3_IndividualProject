package fontys.s3.andreipieleanu.datalayer.entities;

public enum RoleEnum {
    CLIENT(1),
    WORKER(2);
    private final int index;
    RoleEnum(int index){ this.index = index; }
    public int getValue(){ return index; }
}
