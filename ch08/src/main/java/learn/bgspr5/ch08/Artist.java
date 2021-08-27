package learn.bgspr5.ch08;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Artist {
    private Integer id;
    @NonNull
    private String name;
}
