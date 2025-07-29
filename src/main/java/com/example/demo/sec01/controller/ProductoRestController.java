package com.example.demo.sec01.controller;

import com.example.demo.sec01.vo.Producto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class ProductoRestController {
    private final WebClient webClient = WebClient
            .builder()
            .baseUrl("http://localhost:7070")
            .build();

    @GetMapping("/productos")
    public Flux<Producto> obtenerProductos() {
        return webClient
                .get()
                .uri("/demo01/products")
                .retrieve()
                .bodyToFlux(Producto.class)
                .doOnNext(p -> log.info("Recibido: {}", p));
    }
    @GetMapping(value = "/productos/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Producto> obtenerProductosStream() {
        return webClient
                .get()
                .uri("/demo01/products")
                .retrieve()
                .bodyToFlux(Producto.class)
                .doOnNext(p -> log.info("Recibido: {}", p));
    }

}
