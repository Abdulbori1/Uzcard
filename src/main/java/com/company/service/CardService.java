package com.company.service;

import com.company.dto.CardDTO;
import com.company.dto.CardFilterDTO;
import com.company.entity.CardEntity;
import com.company.entity.ClientEntity;
import com.company.enums.CardStatus;
import com.company.exception.EmailAlreadyExistsException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.CardRepository;
import com.company.repository.custom.CardCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CardCustomRepository cardCustomRepository;

    public CardDTO createCard(CardDTO dto) {
        ClientEntity clientEntity = clientService.get(dto.getClient());

        CardEntity entity = new CardEntity();
        entity.setBalance(0L);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setClient(clientEntity);
        entity.setStatus(CardStatus.valueOf(dto.getStatus()));
        if (cardRepository.existsByNumber(dto.getNumber())) {
            throw new EmailAlreadyExistsException("This number exists");
        }
        entity.setNumber(dto.getNumber());
        entity.setPhone(dto.getPhone());
        entity.setExpiredDate(LocalDateTime.now().plusYears(dto.getExpiredDate()));
        entity.setProfile_name(getCurrentProfileUserName());

        cardRepository.save(entity);
        dto.setId(entity.getUuid());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setProfile_name(entity.getProfile_name());
        dto.setBalance(entity.getBalance());

        return dto;
    }

    public CardDTO updateCardStatus(String id, CardDTO dto) {
        Optional<CardEntity> optional = cardRepository.findById(id);
        CardEntity entity = optional.orElseThrow(() -> new ItemNotFoundException("Item not valid!"));

        entity.setStatus(CardStatus.valueOf(dto.getStatus()));

        cardRepository.save(entity);
        dto.setId(entity.getUuid());

        return dto;
    }

    public CardDTO updateCardPhone(String id, CardDTO dto) {
        Optional<CardEntity> optional = cardRepository.findById(id);
        CardEntity entity = optional.orElseThrow(() -> new ItemNotFoundException("Item not valid!"));

        entity.setPhone(dto.getPhone());

        cardRepository.save(entity);
        dto.setId(entity.getUuid());

        return dto;
    }

    public List<CardDTO> getByPhoneList(String phone) {
        List<CardEntity> cardEntityList = cardRepository.findByPhone(phone);
        List<CardDTO> cardDTOList = new LinkedList<>();

        for (CardEntity entity : cardEntityList) {
            cardDTOList.add(toDTO(entity));
        }

        return cardDTOList;
    }

    public List<CardDTO> getByClientIdList(String clientId) {
        ClientEntity clientEntity = clientService.get(clientId);

        List<CardEntity> cardEntityList = cardRepository.findByClient(clientEntity);
        List<CardDTO> cardDTOList = new LinkedList<>();

        for (CardEntity entity : cardEntityList) {
            cardDTOList.add(toDTO(entity));
        }

        return cardDTOList;
    }

    public CardDTO getByNumberList(String number) {
        Optional<CardEntity> cardEntityList = cardRepository.findByNumber(number);
        CardEntity cardEntity = cardEntityList.orElseThrow(() -> new ItemNotFoundException("Item not valid!"));

        return toDTO(cardEntity);
    }

    public Long getByNumberCardBalance(String number) {
        Optional<CardEntity> cardEntityList = cardRepository.findByNumber(number);
        CardEntity cardEntity = cardEntityList.orElseThrow(() -> new ItemNotFoundException("Item not valid!"));

        return cardEntity.getBalance();
    }

    public CardDTO toDTO(CardEntity entity) {
        CardDTO dto = new CardDTO();
        dto.setId(entity.getUuid());
        dto.setBalance(entity.getBalance());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPhone(entity.getPhone());
        dto.setStatus(entity.getStatus().name());
        dto.setNumber(entity.getNumber());
        dto.setProfile_name(entity.getProfile_name());

        return dto;
    }

    public String getCurrentProfileUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // User user = (User) authentication.getPrincipal();
        // List<SimpleGrantedAuthority> rolesLIst = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        // authentication.getCredentials()
        return authentication.getName();
    }

    public CardEntity getCardById(String fromCard) {
        return cardRepository.findById(fromCard).orElseThrow(() -> new ItemNotFoundException("Item not valid!"));
    }


    public List<CardDTO> filter(CardFilterDTO cardFilterDTO) {
        return cardCustomRepository.filter(cardFilterDTO);
    }
}
