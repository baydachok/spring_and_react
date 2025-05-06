package mirea.pracs.productcrud.converter;

import java.util.List;
import mirea.pracs.productcrud.dto.order.OrderGetDto;
import mirea.pracs.productcrud.dto.order.OrderPostDto;
import mirea.pracs.productcrud.dto.order.OrderWrapperDto;
import mirea.pracs.productcrud.dto.orderrecord.OrderRecordGetDto;
import mirea.pracs.productcrud.dto.orderrecord.OrderRecordPostDto;
import mirea.pracs.productcrud.entity.Order;
import mirea.pracs.productcrud.entity.OrderRecord;
import mirea.pracs.productcrud.entity.Product;
import mirea.pracs.productcrud.entity.User;
import mirea.pracs.productcrud.entity.enums.OrderStatus;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {

  public Order convertToOrder(
      OrderPostDto orderPostDto,
      List<ImmutablePair<OrderRecordPostDto, Product>> recordProductPairs,
      User user
  ) {
    var order = new Order()
        .setAddress(orderPostDto.getAddress())
        .setOrderStatus(OrderStatus.PENDING)
        .setUser(user);
    var orderRecords = recordProductPairs
        .stream()
        .map(pair -> convertToOrderRecord(pair.getLeft(), order, pair.getRight()))
        .toList();
    order.setOrderRecords(orderRecords);
    return order;
  }

  public OrderRecord convertToOrderRecord(OrderRecordPostDto orderRecordPostDto, Order order, Product product) {
    return new OrderRecord()
        .setOrder(order)
        .setProduct(product)
        .setQuantity(orderRecordPostDto.getQuantity());
  }

  public OrderGetDto convertToOrderGetDto(Order order) {
    List<OrderRecordGetDto> orderRecordsGetDto = order
        .getOrderRecords()
        .stream()
        .map(this::convertToOrderRecordGetDto)
        .toList();
    return OrderGetDto.builder()
        .orderId(order.getOrderId())
        .orderStatus(order.getOrderStatus().toString())
        .address(order.getAddress())
        .updatedTimestamp(order.getUpdatedTimestamp())
        .orderRecords(orderRecordsGetDto)
        .build();
  }

  public OrderRecordGetDto convertToOrderRecordGetDto(OrderRecord orderRecord) {
    return OrderRecordGetDto.builder()
        .quantity(orderRecord.getQuantity())
        .productId(orderRecord.getProduct().getProductId())
        .build();
  }

  public OrderWrapperDto convertToOrderWrapperDto(List<Order> orders) {
    var ordersGetDto = orders
        .stream()
        .map(this::convertToOrderGetDto)
        .toList();
    return new OrderWrapperDto(ordersGetDto);
  }

}
