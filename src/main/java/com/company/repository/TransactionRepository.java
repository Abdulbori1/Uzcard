package com.company.repository;

import com.company.entity.CardEntity;
import com.company.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
    Page<TransactionEntity> findByFromCardOrToCard(CardEntity fromCard, CardEntity toCard , Pageable pageable);
}
