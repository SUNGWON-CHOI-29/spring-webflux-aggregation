package hello.webflux.springwebfluxaggregation.service;

import hello.webflux.springwebfluxaggregation.dto.Product;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductClient {

    private static final String PRODUCT_BASE_URL = "http://localhost:3000/products/";
    private final WebClient webClient;

    public Mono<Product> getProduct(Integer productId) {
        return this.webClient
            .get()
            .uri(PRODUCT_BASE_URL + "{productId}", productId)
            .retrieve()
            .bodyToMono(Product.class)
            .onErrorResume(ex -> Mono.empty());
    }
}
