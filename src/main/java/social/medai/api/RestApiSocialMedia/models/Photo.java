package social.medai.api.RestApiSocialMedia.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "photos")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Photo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "path")
    private String path;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "post_id",
                referencedColumnName = "id")
    private Post post;

    public Photo(String path){
        this.path = path;
    }
}
