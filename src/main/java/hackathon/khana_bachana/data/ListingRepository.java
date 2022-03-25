package hackathon.khana_bachana.data;

import hackathon.khana_bachana.data.ListingEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<ListingEntity, UUID> {

}
