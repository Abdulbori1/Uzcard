package com.company.repository;

import com.company.entity.CardEntity;
import com.company.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, String> {
    List<CardEntity> findByPhone(String phone);

    List<CardEntity> findByClient(ClientEntity clientEntity);

    Optional<CardEntity> findByNumber(String number);

    boolean existsByNumber(String number);
}
