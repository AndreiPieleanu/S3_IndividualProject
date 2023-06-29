package fontys.s3.andreipieleanu.domain;

public enum Role {
    CLIENT(1),
    WORKER(2);
    private final int index;
    Role(int index){ this.index = index; }
    public int getValue(){ return index; }
}
