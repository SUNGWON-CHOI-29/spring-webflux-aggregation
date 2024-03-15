package hello.webflux.springwebfluxaggregation.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Promotion {

    private String type;
    private Double discount;
    private LocalDate endDate;
}
