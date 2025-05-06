package mirea.pracs.productcrud.service;

import java.util.Objects;
import mirea.pracs.productcrud.converter.OrderConverter;
import mirea.pracs.productcrud.dto.PostResponse;
import mirea.pracs.productcrud.dto.order.OrderGetDto;
import mirea.pracs.productcrud.dto.order.OrderPostDto;
import mirea.pracs.productcrud.dto.order.OrderWrapperDto;
import mirea.pracs.productcrud.entity.Order;
import mirea.pracs.productcrud.entity.User;
import mirea.pracs.productcrud.entity.enums.OrderStatus;
import mirea.pracs.productcrud.entity.enums.UserRole;
import mirea.pracs.productcrud.exceptions.ForbiddenException;
import mirea.pracs.productcrud.exceptions.NotFoundException;
import mirea.pracs.productcrud.repository.OrderRecordRepository;
import mirea.pracs.productcrud.repository.OrderRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderRecordRepository orderRecordRepository;
  private final ProductService productService;
  private final OrderConverter orderConverter;

  public OrderService(OrderRepository orderRepository, OrderRecordRepository orderRecordRepository, ProductService productService, OrderConverter orderConverter) {
    this.orderRepository = orderRepository;
    this.orderRecordRepository = orderRecordRepository;
    this.productService = productService;
    this.orderConverter = orderConverter;
  }

  public OrderGetDto getOrder(Long orderId, User user) {
    var order = orderRepository.findById(orderId)
        .orElseThrow(() -> new NotFoundException(
            String.format("Order with id %d not found", orderId)
        ));
    requireOrderAccessPermission(user, order);
    return orderConverter.convertToOrderGetDto(order);
  }

  private static void requireOrderAccessPermission(User user, Order order) {
    Long authorId = order.getUser().getUserId();
    Long userId = user.getUserId();
    if (!Objects.equals(authorId, userId) &&
        user.getRole().equals(UserRole.USER)) {
      throw new ForbiddenException("User has no permission");
    }
  }

  public OrderWrapperDto getOrders(User user) {
    var orders = orderRepository.findAllByUser(user);
    return orderConverter.convertToOrderWrapperDto(orders);
  }

  @Transactional
  public PostResponse createOrder(OrderPostDto orderPostDto, User user) {
    var pairs = orderPostDto.getOrderRecords()
        .stream()
        .map(recordPostDto -> {
          var product = productService.getProductEntity(recordPostDto.getProductId());
          var newQuantity = product.getQuantityInStock() - recordPostDto.getQuantity();
          if (newQuantity < 0) {
            throw new ForbiddenException("Too many quantity");
          }
          else {
            product.setQuantityInStock(newQuantity);
          }
          return new ImmutablePair<>(recordPostDto, product);
        })
        .toList();
    var order = orderConverter.convertToOrder(orderPostDto, pairs, user);
    orderRepository.save(order);
    orderRecordRepository.saveAll(order.getOrderRecords());
    return new PostResponse(order.getOrderId());
  }

  @Transactional
  public void cancelOrder(Long orderId, User user) {
    var order = orderRepository.findById(orderId)
        .orElseThrow(() -> new NotFoundException(
            String.format("Order with id %d not found", orderId)
        ));
    requireOrderAccessPermission(user, order);
    order.setOrderStatus(OrderStatus.CANCELED);
  }

}
