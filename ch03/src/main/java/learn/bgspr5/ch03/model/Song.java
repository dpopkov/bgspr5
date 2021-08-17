package learn.bgspr5.ch03.model;

import java.util.Objects;

public class Song implements Comparable<Song> {

    private String name;
    private int votes;

    public Song() {
    }

    public Song(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(name, song.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", votes=" + votes +
                '}';
    }

    @Override
    public int compareTo(Song o) {
        int value = Integer.compare(o.getVotes(), getVotes());
        if (value == 0) {
            value = getName().compareTo(o.getName());
        }
        return value;
    }
}
