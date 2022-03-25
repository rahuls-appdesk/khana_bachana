package hackathon.khana_bachana.dtos;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponseDto {

  private UUID id ;
  private UUID producer;
  private UUID consumer;
  private UUID listing;
  private LocalDateTime createdAt;
  private LocalDateTime reservedAt;

}
