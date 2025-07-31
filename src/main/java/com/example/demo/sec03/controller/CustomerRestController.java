package com.example.demo.sec03.controller;


import com.example.demo.sec03.dto.CustomerDto;
import com.example.demo.sec03.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("customers")
public class CustomerRestController {
    private final CustomerService customerService;

    @GetMapping
    public Flux<CustomerDto> getAllCustomers() {
        return customerService
                .getAllCustomers();
    }

    @GetMapping("{id}")
    public Mono<CustomerDto> getCustomerById(@PathVariable Integer id) {
        return customerService
                .findById(id);
    }

    @PostMapping
    public Mono<CustomerDto> save(@RequestBody Mono<CustomerDto> customer){
        return customerService
                .save(customer);
    }

    @PutMapping("{id}")
    public Mono<CustomerDto> update(@RequestBody Mono<CustomerDto> customer, @PathVariable Integer id){
        return customerService
                .update(id, customer);
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable Integer id){
        return customerService
                .deleteById(id);
    }
}
