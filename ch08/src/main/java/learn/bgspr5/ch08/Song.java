package learn.bgspr5.ch08;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Song {
    private Integer id;
    @NonNull
    private Integer artistId;
    @NonNull
    private String name;
    private int votes;
}
