package hackathon.khana_bachana.dtos;

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
  private LocalDateTime expiry;
  private LocalDateTime createdAt;
  private UUID producerId;
}