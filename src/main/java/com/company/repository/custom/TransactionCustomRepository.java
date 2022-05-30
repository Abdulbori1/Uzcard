package com.company.repository.custom;

import com.company.dto.CardDTO;
import com.company.dto.CardFilterDTO;
import com.company.dto.TransactionDTO;
import com.company.dto.TransactionFilterDTO;
import com.company.entity.CardEntity;
import com.company.entity.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TransactionCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public List<TransactionDTO> filter(TransactionFilterDTO filter) {
//        String sql = "SELECT  c FROM  TransactionEntity as c where";
//        Query query = entityManager.createQuery(sql, CardEntity.class);
//        List<CardEntity> cardEntityList = query.getResultList();

        StringBuilder sql = new StringBuilder("SELECT  c FROM TransactionEntity as c ");
        if (filter != null) {
            sql.append(" WHERE c.status = '").append(filter.getStatus().name()).append("'");
        } else {
            sql.append(" WHERE c.status = 'ACTIVE'");
        }

        if (filter.getClient_id() != null) {
            sql.append(" AND  c.fromCard.client.uuid = '").append(filter.getClient_id()).append("' OR  c.toCard.client.uuid = '").append(filter.getClient_id()).append("'");
        }
        if (filter.getCardId() != null) {
            sql.append(" AND  c.fromCard.uuid = '").append(filter.getCardId()).append("' OR  c.toCard.uuid = '").append(filter.getCardId()).append("'");
        }
        if (filter.getCardNumber() != null) {
            sql.append(" AND  c.toCard.number = '").append(filter.getCardNumber()).append("' OR c.fromCard.number = '").append(filter.getCardNumber()).append("'");
        }

        if (filter.getFromAmount() != null && filter.getToAmount() != null) {
            sql.append(" AND  c.amount between ").append(filter.getFromAmount()).append(" AND ").append(filter.getToAmount());
        } else if (filter.getFromAmount() != null) {
            sql.append(" AND  c.amount > ").append(filter.getFromAmount());
        } else if (filter.getToAmount() != null) {
            sql.append(" AND  c.amount < ").append(filter.getToAmount());
        }

        if (filter.getProfile_name() != null) {
            sql.append(" AND  c.fromCard.profile_name = '").append(filter.getProfile_name()).append("'");
        }

        Query query = entityManager.createQuery(sql.toString(), TransactionEntity.class);
        List<TransactionEntity> cardEntityList = query.getResultList();

        return null;
    }

}
