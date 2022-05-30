package com.company.entity;

import com.company.enums.CardStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "card")
public class CardEntity extends BaseEntity{

    @Column
    private String number;
    @Column
    private LocalDateTime expiredDate;
    @Column
    private Long balance;
    @Column
    private String phone;
    @Column
    @Enumerated(EnumType.STRING)
    private CardStatus status;
    @Column
    private LocalDateTime createdDate;
    @Column
    private String profile_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private ClientEntity client;
}
