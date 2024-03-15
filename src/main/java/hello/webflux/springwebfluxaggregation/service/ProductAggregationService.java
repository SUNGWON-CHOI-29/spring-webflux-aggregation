package hello.webflux.springwebfluxaggregation.service;

import hello.webflux.springwebfluxaggregation.dto.Product;
import hello.webflux.springwebfluxaggregation.dto.ProductAggregate;
import hello.webflux.springwebfluxaggregation.dto.Promotion;
import hello.webflux.springwebfluxaggregation.dto.Review;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductAggregationService {

    private final ProductClient productClient;
    private final PromotionClient promotionClient;
    private final ReviewClient reviewClient;

    public Mono<ProductAggregate> getProduct(Integer productId) {
        return Mono.zip(
            productClient.getProduct(productId),
            promotionClient.getPromotion(productId),
            reviewClient.getReviews(productId)
        ).map(this::combine);
    }

    private ProductAggregate combine(Tuple3<Product, Promotion, List<Review>> tuple3) {
        return ProductAggregate.create(
            tuple3.getT1(),
            tuple3.getT2(),
            tuple3.getT3()
        );
    }
}
