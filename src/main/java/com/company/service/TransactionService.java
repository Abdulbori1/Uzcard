package com.company.service;

import com.company.dto.*;
import com.company.entity.CardEntity;
import com.company.entity.ClientEntity;
import com.company.entity.TransactionEntity;
import com.company.enums.CardStatus;
import com.company.enums.ProfileStatus;
import com.company.exception.AppBadRequestException;
import com.company.exception.AppForbiddenException;
import com.company.exception.EmailAlreadyExistsException;
import com.company.repository.CardRepository;
import com.company.repository.TransactionRepository;
import com.company.repository.custom.TransactionCustomRepository;
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

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CardService cardService;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TransactionCustomRepository transactionCustomRepository;

    public TransactionDTO createTransaction(TransactionDTO dto) {
        CardEntity fromCard = cardService.getCardById(dto.getFromCard());
        CardEntity toCard = cardService.getCardById(dto.getToCard());

        TransactionEntity entity = new TransactionEntity();
        if (fromCard.getBalance() <= dto.getAmount()) {
            throw new AppBadRequestException("Balance not enough!");
        }
        entity.setAmount(dto.getAmount());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setStatus(CardStatus.valueOf(dto.getStatus()));
        if (fromCard.equals(toCard)) {
            throw new AppBadRequestException("You can't throw money into your account");
        }
        entity.setFromCard(fromCard);
        entity.setToCard(toCard);

        fromCard.setBalance(fromCard.getBalance() - dto.getAmount());
        toCard.setBalance(toCard.getBalance() + dto.getAmount());

        cardRepository.save(fromCard);
        cardRepository.save(toCard);

        transactionRepository.save(entity);
        dto.setId(entity.getUuid());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public List<TransactionDTO> paginationListCardId(int page, int size, String cardId) {
        CardEntity card = cardService.getCardById(cardId);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<TransactionDTO> dtoList = new ArrayList<>();
        transactionRepository.findByFromCardOrToCard(card, card, pageable).stream().forEach(entity -> {
            dtoList.add(toDTO(entity));
        });

        return dtoList;
    }

    public List<TransactionDTO> paginationListClientId(int page, int size, String clientId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<TransactionDTO> dtoList = new ArrayList<>();
        transactionRepository.findAll(pageable).stream().forEach(entity -> {
            if (entity.getFromCard().getClient().getUuid().equals(clientId)) {
                dtoList.add(toDTO(entity));
            }
        });

        return dtoList;
    }

    public List<TransactionDTO> paginationListPhone(int page, int size, String phone) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<TransactionDTO> dtoList = new ArrayList<>();
        transactionRepository.findAll(pageable).stream().forEach(entity -> {
            if (entity.getFromCard().getPhone().equals(phone)) {
                dtoList.add(toDTO(entity));
            }
        });

        return dtoList;
    }

    public List<TransactionDTO> paginationListProfileName(int page, int size, String profileName) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        List<TransactionDTO> dtoList = new ArrayList<>();
        transactionRepository.findAll(pageable).stream().forEach(entity -> {
            if (entity.getFromCard().getProfile_name().equals(profileName)) {
                dtoList.add(toDTO(entity));
            }
        });

        return dtoList;
    }

    private TransactionDTO toDTO(TransactionEntity entity) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(entity.getAmount());
        transactionDTO.setCreatedDate(entity.getCreatedDate());
        transactionDTO.setStatus(entity.getStatus().name());
        transactionDTO.setFromCard(entity.getFromCard().getUuid());
        transactionDTO.setToCard(entity.getToCard().getUuid());
        transactionDTO.setId(entity.getUuid());

        return transactionDTO;
    }

    public static boolean hasRole(String roleName) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }

    public String getCurrentProfileUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // User user = (User) authentication.getPrincipal();
        // List<SimpleGrantedAuthority> rolesLIst = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        // authentication.getCredentials()
        return authentication.getName();
    }

    public List<TransactionDTO> filter(TransactionFilterDTO cardFilterDTO) {
        return transactionCustomRepository.filter(cardFilterDTO);
    }
}
