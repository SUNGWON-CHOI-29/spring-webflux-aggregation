package hello.webflux.springwebfluxaggregation.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "create")
public class ProductAggregate {

    private Product product;
    private Promotion promotion;
    private List<Review> reviews;
}
