package com.example.demo.sec03.controller;


import com.example.demo.sec03.dto.CustomerDto;
import com.example.demo.sec03.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("paginated")
    public Flux<CustomerDto> getAllCustomers(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "3") Integer size){
        return customerService.getAllCustomers(page, size);
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<CustomerDto>> getCustomerById(@PathVariable Integer id) {
        return customerService
                .findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<CustomerDto> save(@RequestBody Mono<CustomerDto> customer) {
        return customerService
                .save(customer);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<CustomerDto>> update(@RequestBody Mono<CustomerDto> customer, @PathVariable Integer id) {
        return customerService
                .update(id, customer)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Integer id) {
        return customerService
                .deleteById(id)
                .filter(b -> b)
                .map(b -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
