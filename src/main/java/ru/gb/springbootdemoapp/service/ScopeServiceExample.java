package ru.gb.springbootdemoapp.service;

import java.util.Random;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
// @RequestScope
public class ScopeServiceExample {

  private int randomInt;

  public ScopeServiceExample() {
    randomInt = new Random().nextInt(100);
  }

  public int getRandomInt() {
    return randomInt;
  }
}
