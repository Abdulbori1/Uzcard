package com.company.controller;

import com.company.dto.ClientDTO;
import com.company.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@Api(tags = "Client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PreAuthorize("hasRole('bank')")
    @PostMapping("/create")
    @ApiOperation(value = "Create Client", notes = "Method used for create Client BANK", nickname = "Mazgi")
    public ResponseEntity<?> createClient(@RequestBody ClientDTO dto) {
        return ResponseEntity.ok(clientService.createClient(dto));
    }

    @PreAuthorize("hasRole('bank')")
    @PutMapping("/{phone}")
    @ApiOperation(value = "Update Client", notes = "Method used for Update Client BANK", nickname = "Mazgi")
    public ResponseEntity<?> updateClient(@PathVariable("phone") String phone, @RequestBody ClientDTO dto) {
        return ResponseEntity.ok(clientService.updateClient(phone, dto));
    }

    @PreAuthorize("hasRole('bank')")
    @PutMapping("/status/{phone}")
    @ApiOperation(value = "Update Client Status", notes = "Method used for Update Client Status BANK", nickname = "Mazgi")
    public ResponseEntity<?> updateClientStatus(@PathVariable("phone") String phone, @RequestBody ClientDTO dto) {
        return ResponseEntity.ok(clientService.updateClientStatus(phone, dto));
    }

    @PreAuthorize("hasRole('bank')")
    @PutMapping("/phone/{phone}")
    @ApiOperation(value = "Update Client Phone", notes = "Method used for Update Client Phone BANK", nickname = "Mazgi")
    public ResponseEntity<?> updateClientPhone(@PathVariable("phone") String phone, @RequestBody ClientDTO dto) {
        return ResponseEntity.ok(clientService.updateClientPhone(phone, dto));
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/list")
    @ApiOperation(value = "Client List", notes = "Method used for Client List ADMIN", nickname = "Mazgi")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(clientService.paginationList(page, size));
    }

    @PreAuthorize("hasRole('admin') || hasRole('profile')")
    @GetMapping("/{phone}")
    @ApiOperation(value = "Client Get By Phone", notes = "Method used for Client Get By Phone ADMIN or PROFILE", nickname = "Mazgi")
    public ResponseEntity<?> getById(@PathVariable("phone") String phone) {
        return ResponseEntity.ok(clientService.getById(phone));
    }

    @PreAuthorize("hasRole('profile')")
    @GetMapping("/profile/list")
    @ApiOperation(value = "Client Get By Profile Name", notes = "Method used for Client Get By Profile Name ADMIN", nickname = "Mazgi")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(clientService.getByIdProfileName());
    }
}
