package hackathon.khana_bachana.dtos;

import hackathon.khana_bachana.common.ListingStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListingResponseDto {

  private UUID id;
  private String name;
  private String description;
  private String price;
  private String expiry;
  private String createdAt;
  private UUID producerId;
  private ListingStatus listingStatus;
}
