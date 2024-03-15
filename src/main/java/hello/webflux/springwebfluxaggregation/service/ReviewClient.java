package hello.webflux.springwebfluxaggregation.service;

import hello.webflux.springwebfluxaggregation.dto.Review;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewClient {

    private static final String REVIEW_BASE_URL = "http://localhost:3000/reviews";

    private final WebClient webclient;

    public Mono<List<Review>> getReviews(Integer productId) {
        return this.webclient
            .get()
            .uri(REVIEW_BASE_URL, uri -> uri.queryParam("productId", productId).build())
            .retrieve()
            .bodyToFlux(Review.class)
            .collectList()
            .onErrorReturn(Collections.emptyList());
    }
}
