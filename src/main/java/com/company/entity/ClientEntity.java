package com.company.entity;

import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "client")
public class ClientEntity extends BaseEntity{

    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private LocalDateTime createdDate;
    @Column
    private String phone;
    @Column
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
    @Column
    private String middleName;
    @Column
    private String ProfileName;
}
