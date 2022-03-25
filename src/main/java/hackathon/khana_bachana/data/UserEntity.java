package hackathon.khana_bachana.data;

import hackathon.khana_bachana.common.UserType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false)
  private UUID id;
  private String name;
  private String phone;
  private String email;
  private String password;
  @Enumerated(EnumType.STRING)
  private UserType userType;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "producer")
  List<ListingEntity> listingEntityList = new ArrayList<>();
}
