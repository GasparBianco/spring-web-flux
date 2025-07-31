package com.example.demo.sec02;

import com.example.demo.sec02.entity.Customer;
import com.example.demo.sec02.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

@Slf4j
public class Lec01CustomerRepositoryTest extends AbstractTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    public void findAll() {
        repository.findAll()
                       .doOnNext(c -> log.info("{}", c))
                       .as(StepVerifier::create)
                       .expectNextCount(10)
                       .expectComplete()
                       .verify();
    }

    @Test
    public void findById() {
        repository.findById(2)
                       .doOnNext(c -> log.info("{}", c))
                       .as(StepVerifier::create)
                       .assertNext(c -> Assertions.assertEquals("mike", c.getName()))
                       .expectComplete()
                       .verify();
    }

    @Test
    public void findByName() {
        repository.findByName("jake")
                       .doOnNext(c -> log.info("{}", c))
                       .as(StepVerifier::create)
                       .assertNext(c -> Assertions.assertEquals("jake@gmail.com", c.getEmail()))
                       .expectComplete()
                       .verify();
    }

    @Test
    public void findByEmailEndingWith() {
        repository.findByEmailEndingWith("ke@gmail.com")
                       .doOnNext(c -> log.info("{}", c))
                       .as(StepVerifier::create)
                       .assertNext(c -> Assertions.assertEquals("mike@gmail.com", c.getEmail()))
                       .assertNext(c -> Assertions.assertEquals("jake@gmail.com", c.getEmail()))
                       .expectComplete()
                       .verify();
    }

    @Test
    public void insertAndDeleteCustomer() {

        Customer customer = new Customer();
        customer.setName("marshal");
        customer.setEmail("marshal@gmail.com");
        repository.save(customer)
                       .doOnNext(c -> log.info("{}", c))
                       .as(StepVerifier::create)
                       .assertNext(c -> Assertions.assertNotNull(c.getId()))
                       .expectComplete()
                       .verify();

        repository.count()
                       .as(StepVerifier::create)
                       .expectNext(11L)
                       .expectComplete()
                       .verify();

        repository.deleteById(11)
                       .then(repository.count())
                       .as(StepVerifier::create)
                       .expectNext(10L)
                       .expectComplete()
                       .verify();
    }

    @Test
    public void updateCustomer() {
        repository.findByName("ethan")
                       .doOnNext(c -> c.setName("noel"))
                       .flatMap(c -> repository.save(c))
                       .doOnNext(c -> log.info("{}", c))
                       .as(StepVerifier::create)
                       .assertNext(c -> Assertions.assertEquals("noel", c.getName()))
                       .expectComplete()
                       .verify();
    }

}
