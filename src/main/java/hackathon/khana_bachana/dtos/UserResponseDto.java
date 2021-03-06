package hackathon.khana_bachana.dtos;
import hackathon.khana_bachana.common.UserType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
  private UUID id ;
  private String name;
  private String phone;
  private String email;
  private UserType userType;

}
