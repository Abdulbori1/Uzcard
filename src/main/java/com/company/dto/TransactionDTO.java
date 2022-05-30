package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TransactionDTO {
    private String id;
    private String fromCard;
    private String toCard;
    private Long amount;
    private LocalDateTime createdDate;
    private String status;
}
