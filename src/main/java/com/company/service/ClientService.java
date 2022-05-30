package com.company.service;

import com.company.dto.ClientDTO;
import com.company.entity.ClientEntity;
import com.company.enums.ProfileStatus;
import com.company.exception.EmailAlreadyExistsException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public ClientDTO createClient(ClientDTO dto) {
        ClientEntity entity = new ClientEntity();
        if (!clientRepository.existsByPhone(dto.getPhone())) {
            entity.setPhone(dto.getPhone());
        } else {
            throw new EmailAlreadyExistsException("Phone number already exists");
        }
        entity.setSurname(dto.getSurname());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setMiddleName(dto.getMiddleName());
        entity.setName(dto.getName());
        entity.setProfileName(getCurrentProfileUserName());
        entity.setStatus(ProfileStatus.valueOf(dto.getStatus()));

        clientRepository.save(entity);
        dto.setId(entity.getUuid());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setProfileName(entity.getProfileName());

        return dto;
    }

    public ClientDTO updateClient(String phone, ClientDTO dto) {
        ClientEntity entity = getByPhone(phone);
        entity.setSurname(dto.getSurname());
        entity.setMiddleName(dto.getMiddleName());
        entity.setName(dto.getName());

        clientRepository.save(entity);
        dto.setId(entity.getUuid());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPhone(entity.getPhone());

        return dto;
    }

    public ClientDTO updateClientStatus(String phone, ClientDTO dto) {
        ClientEntity entity = getByPhone(phone);
        entity.setStatus(ProfileStatus.valueOf(dto.getStatus()));

        clientRepository.save(entity);
        dto.setId(entity.getUuid());

        return dto;
    }

    public ClientDTO updateClientPhone(String phone, ClientDTO dto) {
        ClientEntity entity = getByPhone(phone);
        entity.setPhone(dto.getPhone());

        clientRepository.save(entity);
        dto.setId(entity.getUuid());

        return dto;
    }

    public List<ClientDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<ClientDTO> dtoList = new ArrayList<>();
        clientRepository.findAll(pageable).stream().forEach(entity -> {
            dtoList.add(toDTO(entity));
        });

        return dtoList;
    }

    public ClientDTO getById(String phone) {
        Optional<ClientEntity> optional = clientRepository.findByPhone(phone);
        ClientEntity entity = optional.orElseThrow(() -> new ItemNotFoundException("Item not valid"));
        return toDTO(entity);
    }

    public List<ClientDTO> getByIdProfileName() {
        List<ClientDTO> dtoList = new ArrayList<>();
        clientRepository.findAll().forEach(entity -> {
            if (entity.getProfileName().equals(getCurrentProfileUserName())) {
                dtoList.add(toDTO(entity));
            }
        });

        return dtoList;
    }

    private ClientDTO toDTO(ClientEntity entity) {
        ClientDTO dto = new ClientDTO();
        dto.setId(entity.getUuid());
        dto.setSurname(entity.getSurname());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setMiddleName(entity.getMiddleName());
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setProfileName(entity.getProfileName());
        dto.setStatus(entity.getStatus().name());

        return dto;
    }

    private ClientEntity getByPhone(String phone) {
        Optional<ClientEntity> optional = clientRepository.findByPhone(phone);
        return optional.orElseThrow(() -> new ItemNotFoundException("Item not valid"));
    }

    public String getCurrentProfileUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // User user = (User) authentication.getPrincipal();
        // List<SimpleGrantedAuthority> rolesLIst = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        // authentication.getCredentials()
        return authentication.getName();
    }

    public ClientEntity get(String client) {
        return clientRepository.findById(client).orElse(null);
    }
}
