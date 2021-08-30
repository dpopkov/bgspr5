package learn.bgspr5.ch09.common;

public interface BaseEntity<ID> {
    ID getId();

    void setId(ID id);
}
