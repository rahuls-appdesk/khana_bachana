package hackathon.khana_bachana.dtos;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class ListingDto {

  private String name;
  private String description;
  private String price;
//  private LocalDateTime expiry;
  private UUID producerId;
}
