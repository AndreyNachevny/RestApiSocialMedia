package social.medai.api.RestApiSocialMedia.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "followers")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Follower {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_follower")
    private Integer idFollower;

    @ManyToOne
    @JoinColumn(name = "id_user",
                referencedColumnName = "id")
    private User user;

}
