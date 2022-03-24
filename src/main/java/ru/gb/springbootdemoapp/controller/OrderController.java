package ru.gb.springbootdemoapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.springbootdemoapp.model.Order;
import ru.gb.springbootdemoapp.model.OrderStatus;
import ru.gb.springbootdemoapp.service.OrderService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping
  public String getOrderPage() {
    return "order";
  }

  @GetMapping("/success")
  public String getOrderSuccessPage() {
    return "order-success";
  }

  @PostMapping
  public String createOrder(@RequestParam String address, @RequestParam String email, Principal principal, Model model) {
    try {
      Order order = orderService.placeOrder(address, email, principal);
      return "redirect:/order/success";
    } catch (IllegalStateException e) {
      model.addAttribute("illegalStateException", e);
      return "order";
    }
  }

  @GetMapping("/list")
  public String getOrderList(Model model) {
    List<Order> orders =  orderService.getAll();
    List<OrderStatus> statuses = List.of(OrderStatus.values());
    model.addAttribute("orders", orders);
    model.addAttribute("statuses", statuses);
    return "order-list";
  }

  @PostMapping("/update/{id}")
  public String updateOrderStatus(@PathVariable Long id){ // , @PathVariable int orderStatus) {
    orderService.updateOrderStatus(id); // захардкодил в OrderRepository 4й статус - не знаю как считать из шаблона thymeleaf значение и передать его в сервис
    return "redirect:/order/list";
  }
}
