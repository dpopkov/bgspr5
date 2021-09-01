package learn.bgspr5.ch09.jpa;

import learn.bgspr5.ch09.common.BaseSong;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(
                name = "artist_song",
                columnList = "artist_id, name",
                unique = true
        )
})
public class Song implements BaseSong<Artist, Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NonNull
    private String name;
    private int votes;
    @ManyToOne
    @NonNull
    private Artist artist;
}
