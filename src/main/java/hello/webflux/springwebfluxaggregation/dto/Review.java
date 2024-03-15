package hello.webflux.springwebfluxaggregation.dto;

import lombok.Data;

@Data
public class Review {

    private String user;
    private Integer rating;
    private String comment;
}
