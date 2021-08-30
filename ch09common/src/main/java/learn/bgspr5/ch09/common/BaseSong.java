package learn.bgspr5.ch09.common;

public interface BaseSong<T extends BaseArtist<ID>, ID> extends BaseEntity<ID> {
    T getArtist();
    void setArtist(T artist);
    String getName();
    void setName(String name);
    int getVotes();
    void setVotes(int votes);
}
