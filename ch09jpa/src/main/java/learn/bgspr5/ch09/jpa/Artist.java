package learn.bgspr5.ch09.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import learn.bgspr5.ch09.common.BaseArtist;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "songs")
public class Artist implements BaseArtist<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NonNull
    private String name;
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "artist",
            fetch = FetchType.EAGER
    )
    @OrderBy("votes DESC")
    @JsonIgnore
    private List<Song> songs = new ArrayList<>();
}
