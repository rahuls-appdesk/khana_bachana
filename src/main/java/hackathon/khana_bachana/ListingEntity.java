package hackathon.khana_bachana;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "listings")
public class ListingEntity {

  @Id
  private UUID id;
  private String name;
  private String description;
  private String price;
  private LocalDateTime createdAt;
  private LocalDateTime expiry;
}
