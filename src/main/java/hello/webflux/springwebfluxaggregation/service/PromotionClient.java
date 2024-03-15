package hello.webflux.springwebfluxaggregation.service;

import hello.webflux.springwebfluxaggregation.dto.Promotion;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PromotionClient {

    private static final String PROMOTION_BASE_URL = "http://localhost:3000/promotions/";
    private final WebClient webClient;
    private final Promotion noPromotion = Promotion.builder()
                                            .type("no-promotion")
                                            .discount(0.0)
                                            .endDate(LocalDate.of(2999, 12, 31))
                                            .build();

    public Mono<Promotion> getPromotion(Integer productId) {
        return this.webClient
            .get()
            .uri(PROMOTION_BASE_URL + "{productId}", productId)
            .retrieve()
            .bodyToMono(Promotion.class)
            .onErrorReturn(noPromotion);
    }
}
