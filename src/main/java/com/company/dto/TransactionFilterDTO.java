package com.company.dto;

import com.company.enums.CardStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class TransactionFilterDTO {
    private String client_id;
    private String cardNumber;
    private String cardId;
    private Long fromAmount;
    private Long toAmount;
    private LocalDateTime created_date;
    private String profile_name;
    private CardStatus status;
}
