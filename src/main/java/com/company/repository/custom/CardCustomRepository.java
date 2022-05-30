package com.company.repository.custom;

import com.company.dto.CardDTO;
import com.company.dto.CardFilterDTO;
import com.company.entity.CardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CardCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public List<CardDTO> filter(CardFilterDTO filter) {
      /*  String sql = "SELECT  c FROM  CardEntity as c";
        Query query = entityManager.createQuery(sql, CardEntity.class);
        List<CardEntity> cardEntityList = query.getResultList();*/

        StringBuilder sql = new StringBuilder("SELECT  c FROM  CardEntity as c ");
        if (filter != null) {
            sql.append(" WHERE c.status = '").append(filter.getStatus().name()).append("'");
        } else {
            sql.append(" WHERE c.status = 'ACTIVE'");
        }

        if (filter.getCardId() != null) {
            sql.append(" AND  c.uuid = '").append(filter.getCardId()).append("'");
        }
        if (filter.getCardNumber() != null) {
            sql.append(" AND  c.number = '").append(filter.getCardId()).append("'");
        }

        if (filter.getFromBalance() != null && filter.getToBalance() != null) {
            sql.append(" AND  c.balance between ").append(filter.getFromBalance()).append(" AND ").append(filter.getToBalance());
        } else if (filter.getFromBalance() != null) {
            sql.append(" AND  c.balance > ").append(filter.getFromBalance());
        } else if (filter.getToBalance() != null) {
            sql.append(" AND  c.balance < ").append(filter.getToBalance());
        }

        if (filter.getProfileName() != null) {
            sql.append(" AND  c.profile_name = '").append(filter.getProfileName()).append("'");
        }

        Query query = entityManager.createQuery(sql.toString(), CardEntity.class);
        List<CardEntity> cardEntityList = query.getResultList();

        return null;
    }

}
