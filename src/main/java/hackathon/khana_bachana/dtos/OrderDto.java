package hackathon.khana_bachana.dtos;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class OrderDto {

  private UUID producer;
  private UUID consumer;
  private UUID listing;
  private LocalDateTime createdAt;
  private LocalDateTime reservedAt;
}
