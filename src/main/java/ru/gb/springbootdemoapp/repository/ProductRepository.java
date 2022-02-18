package ru.gb.springbootdemoapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.springbootdemoapp.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query("select p from Product p left join fetch p.category")
  List<Product> findAll();
}
