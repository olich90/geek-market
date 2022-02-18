package ru.gb.springbootdemoapp.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gb.springbootdemoapp.model.RegistrationToken;
import ru.gb.springbootdemoapp.model.User;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {

  @Query("SELECT rt.user FROM RegistrationToken rt WHERE rt.expiredAt > :time AND rt.token = :token")
  Optional<User> findUserByToken(@Param("time") LocalDateTime time, @Param("token") String token);
}
