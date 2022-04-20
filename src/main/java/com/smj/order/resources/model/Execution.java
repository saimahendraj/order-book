package com.smj.order.resources.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class Execution {

    @NotNull(message = "Instrument cannot be blank")
    private Long instrumentId;
    private Long quantity;
    private double price;
}
