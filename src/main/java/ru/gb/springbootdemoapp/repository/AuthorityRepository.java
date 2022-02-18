package ru.gb.springbootdemoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.springbootdemoapp.model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

  Authority findByName(String name);
}
