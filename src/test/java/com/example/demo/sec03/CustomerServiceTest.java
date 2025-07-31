package com.example.demo.sec03;

import com.example.demo.sec03.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec03")
@Slf4j
public class CustomerServiceTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void allCustomers() {
        client.get()
                   .uri("/customers")
                   .exchange()
                   .expectStatus().is2xxSuccessful()
                   .expectHeader().contentType(MediaType.APPLICATION_JSON)
                   .expectBodyList(CustomerDto.class)
                   .value(list -> log.info("{}", list))
                   .hasSize(10);
    }

    @Test
    public void paginatedCustomers() {
        client.get()
                   .uri("/customers/paginated?page=3&size=2")
                   .exchange()
                   .expectStatus().is2xxSuccessful()
                   .expectBody()
                   .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                   .jsonPath("$.length()").isEqualTo(2)
                   .jsonPath("$[0].id").isEqualTo(5)
                   .jsonPath("$[1].id").isEqualTo(6);
    }

    @Test
    public void customerById() {
        client.get()
                   .uri("/customers/1")
                   .exchange()
                   .expectStatus().is2xxSuccessful()
                   .expectBody()
                   .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                   .jsonPath("$.id").isEqualTo(1)
                   .jsonPath("$.name").isEqualTo("sam")
                   .jsonPath("$.email").isEqualTo("sam@gmail.com");
    }

    @Test
    public void createAndDeleteCustomer() {
        CustomerDto dto = new CustomerDto(null, "marshal", "marshal@gmail.com");
        client.post()
                   .uri("/customers")
                   .bodyValue(dto)
                   .exchange()
                   .expectStatus().is2xxSuccessful()
                   .expectBody()
                   .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                   .jsonPath("$.id").isEqualTo(11)
                   .jsonPath("$.name").isEqualTo("marshal")
                   .jsonPath("$.email").isEqualTo("marshal@gmail.com");

        client.delete()
                   .uri("/customers/11")
                   .exchange()
                   .expectStatus().is2xxSuccessful()
                   .expectBody().isEmpty();
    }

    @Test
    public void updateCustomer() {
        CustomerDto dto = new CustomerDto(null, "noel", "noel@gmail.com");
        client.put()
                   .uri("/customers/10")
                   .bodyValue(dto)
                   .exchange()
                   .expectStatus().is2xxSuccessful()
                   .expectBody()
                   .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                   .jsonPath("$.id").isEqualTo(10)
                   .jsonPath("$.name").isEqualTo("noel")
                   .jsonPath("$.email").isEqualTo("noel@gmail.com");
    }

    @Test
    public void customerNotFound() {
        client.get()
                   .uri("/customers/11")
                   .exchange()
                   .expectStatus().is4xxClientError()
                   .expectBody().isEmpty();


        client.delete()
                   .uri("/customers/11")
                   .exchange()
                   .expectStatus().is4xxClientError()
                   .expectBody().isEmpty();

        CustomerDto dto = new CustomerDto(null, "noel", "noel@gmail.com");
        client.put()
                   .uri("/customers/11")
                   .bodyValue(dto)
                   .exchange()
                   .expectStatus().is4xxClientError()
                   .expectBody().isEmpty();
    }

}
