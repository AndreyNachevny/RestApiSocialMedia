package social.medai.api.RestApiSocialMedia.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "Friends")
public class Friend {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_friend")
    private Integer idFriend;

    @ManyToOne
    @JoinColumn(name = "id_user",
                referencedColumnName = "id")
    private User user;

}
