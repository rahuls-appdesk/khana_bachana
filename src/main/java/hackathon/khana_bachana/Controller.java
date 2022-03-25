package hackathon.khana_bachana;

import hackathon.khana_bachana.data.OrderEntity;
import hackathon.khana_bachana.data.UserEntity;
import hackathon.khana_bachana.dtos.ListingDto;
import hackathon.khana_bachana.dtos.ListingResponseDto;
import hackathon.khana_bachana.dtos.OrderDto;
import hackathon.khana_bachana.dtos.OrderResponseDto;
import hackathon.khana_bachana.dtos.UserDto;
import hackathon.khana_bachana.dtos.UserResponseDto;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/")
public class Controller {

  private final Service service;

  public Controller(Service service) {
    this.service = service;
  }

  @PostMapping("/user/add")
  public UserResponseDto addUser(@RequestBody UserDto userDto) {
    return service.addUser(userDto);
  }

  @GetMapping("/user/get/{id}")
  public UserResponseDto getUser(@PathVariable("id") UUID userId) {
    return service.getUser(userId);
  }

  @PostMapping("/listing/add")
  public ListingResponseDto addListing(@RequestBody ListingDto listingDto) {
    return service.addListing(listingDto);
  }

  @GetMapping("/listing/get/{id}")
  public ListingResponseDto getListing(@PathVariable("id") UUID listingId) {
    return service.getListing(listingId);
  }

  @PostMapping("/order/add")
  public OrderResponseDto addOrder(@RequestBody OrderDto orderDto) {
    return service.addOrder(orderDto);
  }

  @GetMapping("/order/get/{id}")
  public OrderResponseDto getOrder(@PathVariable("id") UUID orderId) {
    return service.getOrder(orderId);
  }

  @PutMapping("order/close/{id}")
  public String closeListing(@PathVariable("id") UUID orderId) {
    return service.closeOrderListing(orderId);
  }

  @PutMapping("order/cancel/{id}")
  public String cancelOrderListing(@PathVariable("id") UUID orderId) {
    return service.closeOrderListing(orderId);
  }
}
