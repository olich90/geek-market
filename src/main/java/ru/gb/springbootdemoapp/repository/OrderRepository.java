package ru.gb.springbootdemoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.springbootdemoapp.model.Order;
import ru.gb.springbootdemoapp.model.OrderStatus;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("FROM Order o WHERE o.orderStatus = :status")
    List<Order> findAllByOrderStatusEquals(@Param("status") OrderStatus status);

    @Query("select o from Order o")
    List<Order> findAll();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Order o set o.orderStatus = 4 where o.id = :id")
    void updateOrderStatus(@Param("id") Long id);
}
