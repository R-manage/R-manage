package com.rmanage.rmanage.customerService.service;

import com.rmanage.rmanage.CustomerServiceRespository;
import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.customerService.dto.CustomerServiceRequestDto;
import com.rmanage.rmanage.customerService.dto.CustomerServiceResponseDto;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.setting.dto.EmailResponseDto;
import com.rmanage.rmanage.setting.dto.EmailResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerServiceService {

    final CustomerServiceRespository customerServiceRespository;
    final UserRepository userRepository;

    public CustomerServiceResponseDto saveCustomerServiceById(CustomerServiceRequestDto customerServiceRequestDto) {

        try {
            Optional<User> entity = userRepository.findById(customerServiceRequestDto.getUserId());
            if(entity.isEmpty()){
                return new CustomerServiceResponseDto(false,3012,"해당하는 근로자 정보가 없음",null);
            }
            User user = entity.get();
            // 조회 성공
            customerServiceRespository.save(customerServiceRequestDto.CustomerServicetoEntity(user));

            return new CustomerServiceResponseDto(true,1011,"고객센터 문의 성공", null);
        }   catch (Exception e){
            System.out.println(e);
            return new CustomerServiceResponseDto(false,3035,"고객센터 문의 실패",null);
        }

    }

}
