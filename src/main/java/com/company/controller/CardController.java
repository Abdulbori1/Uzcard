package com.company.controller;

import com.company.dto.CardDTO;
import com.company.dto.CardFilterDTO;
import com.company.dto.ClientDTO;
import com.company.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
@Api(tags = "Card")
public class CardController {
    @Autowired
    private CardService cardService;

    @PreAuthorize("hasRole('bank')")
    @PostMapping("/bank/create")
    @ApiOperation(value = "Create Card", notes = "Method used for create Card BANK", nickname = "Mazgi")
    public ResponseEntity<?> createCard(@RequestBody CardDTO dto) {
        return ResponseEntity.ok(cardService.createCard(dto));
    }

    @PreAuthorize("hasRole('bank')")
    @PutMapping("/bank/status/{id}")
    @ApiOperation(value = "Update Card Status", notes = "Method used for Update Card Status BANK", nickname = "Mazgi")
    public ResponseEntity<?> updateCardStatus(@PathVariable("id") String id, @RequestBody CardDTO dto) {
        return ResponseEntity.ok(cardService.updateCardStatus(id, dto));
    }

    @PreAuthorize("hasRole('bank')")
    @PutMapping("/bank/phone/{id}")
    @ApiOperation(value = "Update Card Phone", notes = "Method used for update Card Phone BANK", nickname = "Mazgi")
    public ResponseEntity<?> updateCardPhone(@PathVariable("id") String id, @RequestBody CardDTO dto) {
        return ResponseEntity.ok(cardService.updateCardPhone(id, dto));
    }

    @ApiOperation(value = "Get Card Phone List", notes = "Method used for Get Card Phone List", nickname = "Mazgi")
    @GetMapping("/any/List/phone/{phone}")
    public ResponseEntity<?> getByPhoneList(@PathVariable("phone") String phone) {
        return ResponseEntity.ok(cardService.getByPhoneList(phone));
    }

    @ApiOperation(value = "Get Card ClientId List", notes = "Method used for Get Card ClientId List", nickname = "Mazgi")
    @GetMapping("/any/List/clientId/{clientId}")
    public ResponseEntity<?> getByClientIdList(@PathVariable("clientId") String clientId) {
        return ResponseEntity.ok(cardService.getByClientIdList(clientId));
    }

    @ApiOperation(value = "Get Card Number", notes = "Method used for Get Card Number", nickname = "Mazgi")
    @GetMapping("/any/number/{number}")
    public ResponseEntity<?> getByNumberList(@PathVariable("number") String number) {
        return ResponseEntity.ok(cardService.getByNumberList(number));
    }

    @ApiOperation(value = "Get Balance Card Number", notes = "Method used for Get Card Number", nickname = "Mazgi")
    @GetMapping("/any/balance/{number}")
    public ResponseEntity<?> getByNumberCardBalance(@PathVariable("number") String number) {
        return ResponseEntity.ok(cardService.getByNumberCardBalance(number));
    }

    @ApiOperation(value = "Filter Card", notes = "Method used for Filter Card", nickname = "Mazgi")
    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody CardFilterDTO filterDTO) {
        return ResponseEntity.ok(cardService.filter(filterDTO));
    }
}
