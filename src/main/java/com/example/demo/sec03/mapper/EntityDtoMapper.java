package com.example.demo.sec03.mapper;

import com.example.demo.sec03.dto.CustomerDto;
import com.example.demo.sec03.entity.Customer;

public class EntityDtoMapper {

    public static Customer toEntity(CustomerDto dto){
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setId(dto.id());
        return customer;
    }

    public static CustomerDto toDto(Customer customer){
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }

}
