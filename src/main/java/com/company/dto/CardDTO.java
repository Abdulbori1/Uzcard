package com.company.dto;

import com.company.enums.CardStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
public class CardDTO {
    private String id;
    private String number;
    private Integer expiredDate;
    private Long balance;
    private String phone;
    private String client;
    private String status;
    private LocalDateTime createdDate;
    private String profile_name;
}
