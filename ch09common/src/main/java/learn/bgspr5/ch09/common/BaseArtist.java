package learn.bgspr5.ch09.common;

public interface BaseArtist<ID> extends BaseEntity<ID> {
    String getName();

    void setName(String name);
}
