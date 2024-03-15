package hello.webflux.springwebfluxaggregation.controller;

import hello.webflux.springwebfluxaggregation.dto.ProductAggregate;
import hello.webflux.springwebfluxaggregation.service.ProductAggregationService;
import reactor.core.publisher.Mono;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductAggregateController {

    private final ProductAggregationService productAggregationService;

    @GetMapping("/{productId}")
    public Mono<ResponseEntity<ProductAggregate>> getProduct(@PathVariable Integer productId) {
        return productAggregationService.getProduct(productId)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
