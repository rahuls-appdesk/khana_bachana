package hackathon.khana_bachana;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class UserEntity {

  @Id
  private UUID id;
  private String name;
  private String phone;
  private String email;
  private String password;
  @Enumerated(EnumType.STRING)
  private UserType userType;
}
