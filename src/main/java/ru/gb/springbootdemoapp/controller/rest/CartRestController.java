package ru.gb.springbootdemoapp.controller.rest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.springbootdemoapp.dto.Cart;
import ru.gb.springbootdemoapp.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartRestController {

  private CartService cartService;

  public CartRestController(CartService cartService) {
    this.cartService = cartService;
  }

  @GetMapping// GET cart
  public Cart getCart() {
    return cartService.getCartForCurrentUser();
  }

  @PostMapping("/product/{id}") // POST cart/product/1
  public Cart addProduct(@PathVariable Long id) {
    return cartService.addProductById(id);
  }

  @DeleteMapping("/product/{id}") // DELETE cart/product/1
  public Cart deleteProduct(@PathVariable Long id) {
    return cartService.removeProductById(id);
  }
}
