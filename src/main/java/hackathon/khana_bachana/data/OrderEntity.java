package hackathon.khana_bachana.data;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name = "orders")
public class OrderEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "producer_id",
      referencedColumnName = "id",
      columnDefinition = "binary(16)",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_usersp_orders"))
  private UserEntity producer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "consumer_id",
      referencedColumnName = "id",
      columnDefinition = "binary(16)",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_usersc_orders"))
  private UserEntity consumer;

  @OneToOne
  @JoinColumn(
      name = "listing_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_listings_orders"))
  private ListingEntity listing;

//  @Enumerated(EnumType.STRING)
//  private ListingStatus listingStatus;

  private LocalDateTime createdAt;
  private LocalDateTime reservedAt;
}
