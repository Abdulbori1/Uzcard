package com.company.dto;

import com.company.enums.CardStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardFilterDTO {
    private String clientId;
    private String cardNumber;
    private String cardId;

    private Long fromBalance;
    private Long toBalance;
    private String profileName;
    private CardStatus status;

}
