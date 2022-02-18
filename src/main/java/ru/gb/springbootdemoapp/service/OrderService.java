package ru.gb.springbootdemoapp.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.springbootdemoapp.dto.Cart;
import ru.gb.springbootdemoapp.model.Order;
import ru.gb.springbootdemoapp.model.OrderItem;
import ru.gb.springbootdemoapp.model.OrderStatus;
import ru.gb.springbootdemoapp.model.ShippingMethod;
import ru.gb.springbootdemoapp.model.User;
import ru.gb.springbootdemoapp.repository.OrderRepository;
import ru.gb.springbootdemoapp.repository.ProductRepository;
import ru.gb.springbootdemoapp.repository.UserRepository;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final CartService cartService;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;

  public OrderService(OrderRepository orderRepository,
                      CartService cartService,
                      UserRepository userRepository,
                      ProductRepository productRepository) {
    this.orderRepository = orderRepository;
    this.cartService = cartService;
    this.userRepository = userRepository;
    this.productRepository = productRepository;
  }

  @Transactional
  public Order placeOrder(String address, String email, Principal principal) {
    Cart cart = cartService.getCartForCurrentUser();
    if (cart.getItems().isEmpty()) {
      throw new IllegalStateException("Корзина пуста");
    }

    User user = principal != null ? userRepository.findByEmail(principal.getName()).orElse(null) : null;

    Order order = new Order();
    order.setCustomer(user);
    order.setPrice(cart.getPrice());
    order.setOrderStatus(OrderStatus.NEW);
    order.setShippingMethod(ShippingMethod.DELIVERY);
    order.setAddress(address);
    order.setContactEmail(email);
    order.setCreationTime(LocalDateTime.now());

    List<OrderItem> orderItems = cart.getItems().stream()
        .map(cartItem -> {
          OrderItem orderItem = new OrderItem();
          orderItem.setOrder(order);
          orderItem.setQuantity(cartItem.getCount());
          orderItem.setPrice(cartItem.getPrice());
          orderItem.setProduct(productRepository.getById(cartItem.getProductId()));
          return orderItem;
        }).collect(Collectors.toList());

    order.setOrderItems(orderItems);

    orderRepository.save(order);
    cartService.init();
    return order;
  }
}
