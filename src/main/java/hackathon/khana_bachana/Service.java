package hackathon.khana_bachana;

import hackathon.khana_bachana.common.ListingStatus;
import hackathon.khana_bachana.data.ListingEntity;
import hackathon.khana_bachana.data.ListingRepository;
import hackathon.khana_bachana.data.OrderEntity;
import hackathon.khana_bachana.data.OrderRepository;
import hackathon.khana_bachana.data.UserEntity;
import hackathon.khana_bachana.data.UserRepository;
import hackathon.khana_bachana.dtos.ListingDto;
import hackathon.khana_bachana.dtos.ListingResponseDto;
import hackathon.khana_bachana.dtos.OrderDto;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.transaction.Transactional;

@org.springframework.stereotype.Service
public class Service {

  private final UserRepository userRepository;
  private final ListingRepository listingRepository;
  private final OrderRepository orderRepository;

  public Service(UserRepository userRepository,
      ListingRepository listingRepository,
      OrderRepository orderRepository) {
    this.userRepository = userRepository;
    this.listingRepository = listingRepository;
    this.orderRepository = orderRepository;
  }

  public UserEntity addUser(UserEntity userEntity) {
    return userRepository.save(userEntity);
  }

  public ListingResponseDto addListing(ListingDto listingDto) {
    ListingEntity listingEntity = new ListingEntity();
    listingEntity.setName(listingDto.getName());
    UserEntity userEntity = userRepository.getById(listingDto.getProducerId());
    listingEntity.setProducer(userEntity);
    userEntity.getListingEntityList().add(listingEntity);
    listingEntity.setDescription(listingDto.getDescription());
    listingEntity.setPrice(listingDto.getPrice());
    listingEntity.setCreatedAt(LocalDateTime.now());
    listingEntity.setExpiry(LocalDateTime.now().plusHours(3));
    listingRepository.save(listingEntity);
    return buildListingResponse(listingEntity);
  }

  public OrderEntity addOrder(OrderDto orderDto) {
    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setProducer(userRepository.findById(orderDto.getProducer()).get());
    orderEntity.setConsumer(userRepository.findById(orderDto.getConsumer()).get());
    ListingEntity listingEntity = listingRepository.findById(orderDto.getListing()).get();
    orderEntity.setListing(listingEntity);
    listingEntity.setListingStatus(ListingStatus.RESERVED);
    orderEntity.setCreatedAt(LocalDateTime.now());
    return orderRepository.save(orderEntity);
  }

  @Transactional
  public String closeOrderListing(UUID orderId) {
    OrderEntity orderEntity = orderRepository.getById(orderId);
    orderEntity.getListing().setListingStatus(ListingStatus.CLOSED);
    orderRepository.delete(orderEntity);
    return "Order closed";
  }

  public String cancelReservation(UUID orderId) {
    OrderEntity orderEntity = orderRepository.getById(orderId);
    orderEntity.getListing().setListingStatus(ListingStatus.LISTED);
    if (LocalDateTime.now().isAfter(orderEntity.getReservedAt().plusMinutes(10))) {
      orderRepository.delete(orderEntity);
    }
    return "Order cancelled";
  }

  public UserEntity getUser(UUID userId) {
    return userRepository.findById(userId).get();
  }

  public ListingResponseDto getListing(UUID listingId) {
    return buildListingResponse(listingRepository.findById(listingId).get());
  }

  public OrderEntity getOrder(UUID orderId) {
    return orderRepository.findById(orderId).get();
  }

  private ListingResponseDto buildListingResponse(ListingEntity listingEntity) {
    return ListingResponseDto.builder()
        .id(listingEntity.getId())
        .name(listingEntity.getName())
        .description(listingEntity.getDescription())
        .createdAt(listingEntity.getCreatedAt())
        .expiry(listingEntity.getExpiry())
        .price(listingEntity.getPrice())
        .producerId(listingEntity.getProducer().getId())
        .build();
  }


}
