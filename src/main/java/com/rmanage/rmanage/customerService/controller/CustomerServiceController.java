package com.rmanage.rmanage.customerService.controller;

import com.rmanage.rmanage.customerService.dto.CustomerServiceRequestDto;
import com.rmanage.rmanage.customerService.dto.CustomerServiceResponseDto;
import com.rmanage.rmanage.customerService.service.CustomerServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CustomerServiceController {
    private final CustomerServiceService customerServiceService;

    @PostMapping("/every/customer/service")
    public ResponseEntity<CustomerServiceResponseDto> saveCustomerService(@RequestBody CustomerServiceRequestDto customerServiceRequestDto) {
        CustomerServiceResponseDto findUser = customerServiceService.saveCustomerServiceById(customerServiceRequestDto);

        return ResponseEntity.ok().body(findUser);
    }
}
