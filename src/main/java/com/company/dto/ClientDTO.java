package com.company.dto;

import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ClientDTO {
    private String id;
    private String name;
    private String surname;
    private String middleName;
    private LocalDateTime createdDate;
    private String phone;
    private String status;
    private String ProfileName;
}
