package com.bookstore.model.repositories;

import com.bookstore.model.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    ClientEntity getByEmail(String email);
    List<ClientEntity> findByEmailIn(List<String> email);
}
