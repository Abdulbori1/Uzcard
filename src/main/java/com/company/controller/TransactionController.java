package com.company.controller;

import com.company.dto.TransactionDTO;
import com.company.dto.TransactionFilterDTO;
import com.company.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@Api(tags = "Client")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PreAuthorize("hasRole('bank')")
    @PostMapping("/create")
    @ApiOperation(value = "Create Transaction", notes = "Method used for Create Transaction BANK", nickname = "Mazgi")
    public ResponseEntity<?> createClient(@RequestBody TransactionDTO dto) {
        return ResponseEntity.ok(transactionService.createTransaction(dto));
    }

    @PreAuthorize("hasRole('bank') || hasRole('client')")
    @GetMapping("/pagination/cardId/list/{id}")
    @ApiOperation(value = "Pagination List CardId Transaction", notes = "Method used for Pagination List CardId Transaction BANK or CLIENT", nickname = "Mazgi")
    public ResponseEntity<?> paginationListCardId(@PathVariable("id") String id,
                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(transactionService.paginationListCardId(page, size, id));
    }

    @PreAuthorize("hasRole('bank') || hasRole('client')")
    @GetMapping("/pagination/clientId/{id}")
    @ApiOperation(value = "Pagination List ClientId Transaction", notes = "Method used for Pagination List ClientId Transaction BANK", nickname = "Mazgi")
    public ResponseEntity<?> paginationListClientId(@PathVariable("id") String id,
                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(transactionService.paginationListClientId(page, size, id));
    }

    @PreAuthorize("hasRole('bank') || hasRole('client')")
    @GetMapping("/pagination/clientId/phone/{phone}")
    @ApiOperation(value = "Pagination List Phone Transaction", notes = "Method used for Pagination List Phone Transaction BANK", nickname = "Mazgi")
    public ResponseEntity<?> paginationListPhone(@PathVariable("phone") String phone,
                                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                                    @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(transactionService.paginationListPhone(page, size, phone));
    }

    @PreAuthorize("hasRole('bank') || hasRole('client')")
    @GetMapping("/pagination/clientId/list/{profileName}")
    @ApiOperation(value = "Pagination List ProfileName Transaction", notes = "Method used for Pagination List ProfileName Transaction BANK", nickname = "Mazgi")
    public ResponseEntity<?> paginationListProfileName(@PathVariable("profileName") String profileName,
                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(transactionService.paginationListProfileName(page, size, profileName));
    }

    @PostMapping("/filter")
    @ApiOperation(value = "Filter Transaction", notes = "Method used for Filter Transaction BANK", nickname = "Mazgi")
    public ResponseEntity<?> filter(@RequestBody TransactionFilterDTO filterDTO) {
        return ResponseEntity.ok(transactionService.filter(filterDTO));
    }
}
