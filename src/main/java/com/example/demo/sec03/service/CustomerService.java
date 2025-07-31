package com.example.demo.sec03.service;

import com.example.demo.sec03.dto.CustomerDto;
import com.example.demo.sec03.mapper.EntityDtoMapper;
import com.example.demo.sec03.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Flux<CustomerDto> getAllCustomers() {
        return customerRepository
                .findAll()
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> findById(Integer id) {
        return customerRepository
                .findById(id)
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> save(Mono<CustomerDto> customer) {
        return customer
                .map(EntityDtoMapper::toEntity)
                .flatMap(customerRepository::save)
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> update(Integer id, Mono<CustomerDto> customer) {
        return customerRepository
                .findById(id)
                .flatMap(c -> customer)
                .map(EntityDtoMapper::toEntity)
                .doOnNext(c -> c.setId(id))
                .flatMap(customerRepository::save)
                .map(EntityDtoMapper::toDto);
    }

    public Mono<Void> deleteById(Integer id){
        return customerRepository.deleteById(id);
    }
}
