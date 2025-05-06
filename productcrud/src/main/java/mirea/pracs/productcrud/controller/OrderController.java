package mirea.pracs.productcrud.controller;

import mirea.pracs.productcrud.dto.PostResponse;
import mirea.pracs.productcrud.dto.order.OrderGetDto;
import mirea.pracs.productcrud.dto.order.OrderPostDto;
import mirea.pracs.productcrud.dto.order.OrderWrapperDto;
import mirea.pracs.productcrud.service.OrderService;
import mirea.pracs.productcrud.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;
  private final UserService userService;

  public OrderController(OrderService orderService, UserService userService) {
    this.orderService = orderService;
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<OrderWrapperDto> getOrders(Authentication authentication) {
    var user = userService.findOneByAuthentication(authentication);
    var orderWrapperDto = orderService.getOrders(user);
    return ResponseEntity.ok(orderWrapperDto);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderGetDto> getOrder(
      @PathVariable("orderId") Long orderId,
      Authentication authentication
  ) {
    var user = userService.findOneByAuthentication(authentication);
    var order = orderService.getOrder(orderId, user);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(order);
  }

  @PostMapping
  public ResponseEntity<PostResponse> createOrder(
      @RequestBody OrderPostDto orderPostDto,
      Authentication authentication
  ) {
    var user = userService.findOneByAuthentication(authentication);
    var postResponse = orderService.createOrder(orderPostDto, user);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(postResponse);
  }

  @PatchMapping("/{orderId}/cancel")
  public ResponseEntity<Void> cancelOrder(
      @PathVariable("orderId") Long orderId,
      Authentication authentication
  ) {
    var user = userService.findOneByAuthentication(authentication);
    orderService.cancelOrder(orderId, user);
    return ResponseEntity
        .status(HttpStatus.OK)
        .build();
  }

}
