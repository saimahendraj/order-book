package com.smj.order.resources.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class Order {

    private Long orderId;

    private Long orderBookId;

    private Long orderQuantity;

    private double price;

    private LocalDateTime entryDate;

    @NotNull(message = "Instrument cannot be blank")
    private Long instrumentId;

    private String orderType;
}
