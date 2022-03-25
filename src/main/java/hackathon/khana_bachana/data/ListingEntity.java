package hackathon.khana_bachana.data;

import hackathon.khana_bachana.common.ListingStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name = "listings")
public class ListingEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false)
  private UUID id;
  private String name;
  private String description;
  private String price;
  private LocalDateTime createdAt;
  private LocalDateTime expiry;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "producer_id",
      referencedColumnName = "id",
      columnDefinition = "binary(16)",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_usersp_listings"))
  private UserEntity producer;

  @Enumerated(EnumType.STRING)
  private ListingStatus listingStatus = ListingStatus.LISTED;
}
