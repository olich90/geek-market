package ru.gb.springbootdemoapp.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.springbootdemoapp.dto.Cart;
import ru.gb.springbootdemoapp.service.ScopeServiceExample;

/**
 * Это контроллер для демонстрации различных скоупов и как их внедрять в синглтон бины
 * + пример с сессией.
 */
@RestController
@RequestMapping("/scope")
public class ScopeControllerExample {

  @Autowired
  private ScopeServiceExample scopeServiceExample;

  @GetMapping
  public String getIntExample(HttpSession session) {
    session.setAttribute("cart-example", new Cart());
    return scopeServiceExample.getClass() + " random int 1: " +
           scopeServiceExample.getRandomInt() +
           " random int 2: " + scopeServiceExample.getRandomInt() ;
  }

  @Lookup // Чтобы доставать Prototype
  public ScopeServiceExample scopeServiceExample() {
    return null;
  }
}
