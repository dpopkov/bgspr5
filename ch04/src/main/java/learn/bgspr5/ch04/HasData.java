package learn.bgspr5.ch04;

import java.util.Objects;

abstract class HasData {
    String datum = "default";

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HasData)) return false;
        HasData hasData = (HasData) o;
        return Objects.equals(datum, hasData.datum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datum);
    }
}
