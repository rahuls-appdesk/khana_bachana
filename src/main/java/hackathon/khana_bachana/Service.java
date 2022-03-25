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
import hackathon.khana_bachana.dtos.OrderResponseDto;
import hackathon.khana_bachana.dtos.UserDto;
import hackathon.khana_bachana.dtos.UserResponseDto;
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

  public UserResponseDto addUser(UserDto userDto) {
    UserEntity userEntity = new UserEntity();
    userEntity.setName(userDto.getName());
    userEntity.setEmail(userDto.getEmail());
    userEntity.setPhone(userDto.getPhone());
    userEntity.setPassword(userDto.getPassword());
    userEntity.setUserType(userDto.getUserType());
    userRepository.save(userEntity);
    return buildUserResponse(userEntity);
  }

  public ListingResponseDto addListing(ListingDto listingDto) {
    ListingEntity listingEntity = new ListingEntity();
    listingEntity.setName(listingDto.getName());
    UserEntity producer = getUserEntity(listingDto.getProducerId());
    listingEntity.setProducer(producer);
    producer.getListingEntityList().add(listingEntity);
    listingEntity.setDescription(listingDto.getDescription());
    listingEntity.setPrice(listingDto.getPrice());
    listingEntity.setCreatedAt(LocalDateTime.now());
    listingEntity.setExpiry(LocalDateTime.now().plusHours(3));
    listingRepository.save(listingEntity);
    return buildListingResponse(listingEntity);
  }

  public OrderResponseDto addOrder(OrderDto orderDto) {
    OrderEntity orderEntity = new OrderEntity();
    UserEntity producer = getUserEntity(orderDto.getProducer());
    UserEntity consumer = getUserEntity(orderDto.getConsumer());
    orderEntity.setProducer(producer);
    producer.getProducerOrderEntityList().add(orderEntity);
    orderEntity.setConsumer(consumer);
    consumer.getConsumerOrderEntityList().add(orderEntity);
    ListingEntity listingEntity = getListingEntity(orderDto.getListing());
    orderEntity.setListing(listingEntity);
    listingEntity.setListingStatus(ListingStatus.RESERVED);
    orderEntity.setCreatedAt(LocalDateTime.now());
    orderEntity.setReservedAt(orderDto.getReservedAt());
    orderRepository.save(orderEntity);
    return buildOrderResponse(orderEntity);
  }

  @Transactional
  public String closeOrderListing(UUID orderId) {
    OrderEntity orderEntity = getOrderEntity(orderId);
    orderEntity.getListing().setListingStatus(ListingStatus.CLOSED);
    orderRepository.delete(orderEntity);
    return "Order closed";
  }

  public String cancelReservation(UUID orderId) {
    OrderEntity orderEntity = getOrderEntity(orderId);
    orderEntity.getListing().setListingStatus(ListingStatus.LISTED);
    if (LocalDateTime.now().isAfter(orderEntity.getReservedAt().plusMinutes(10))) {
      orderRepository.delete(orderEntity);
    }
    return "Order cancelled";
  }

  public UserResponseDto getUser(UUID userId) {
    return buildUserResponse(getUserEntity(userId));
  }

  public ListingResponseDto getListing(UUID listingId) {
    return buildListingResponse(getListingEntity(listingId));
  }

  public OrderResponseDto getOrder(UUID orderId) {
    return buildOrderResponse(getOrderEntity(orderId));
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
        .listingStatus(listingEntity.getListingStatus())
        .build();
  }

  private OrderResponseDto buildOrderResponse(OrderEntity orderEntity) {
    return OrderResponseDto.builder()
        .id(orderEntity.getId())
        .producer(orderEntity.getProducer().getId())
        .consumer(orderEntity.getConsumer().getId())
        .listing(orderEntity.getListing().getId())
        .createdAt(orderEntity.getCreatedAt())
        .reservedAt(orderEntity.getReservedAt())
        .build();
  }

  private UserResponseDto buildUserResponse(UserEntity userEntity) {
    return UserResponseDto.builder()
        .id(userEntity.getId())
        .name(userEntity.getName())
        .phone(userEntity.getPhone())
        .email(userEntity.getEmail())
        .userType(userEntity.getUserType())
        .build();
  }

  private ListingEntity getListingEntity(UUID listingId) {
    return listingRepository.findById(listingId)
        .orElseThrow(() -> new RuntimeException("No listing entity"));
  }

  private OrderEntity getOrderEntity(UUID orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("No order entity"));
  }

  private UserEntity getUserEntity(UUID userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("No user entity"));
  }

}
