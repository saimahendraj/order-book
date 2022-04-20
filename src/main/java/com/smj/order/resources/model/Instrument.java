package com.smj.order.resources.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class Instrument {

    @NotNull(message = "Instrument cannot be blank")
    private Long instrumentId;

    private String instrumentName;

    private String orderBookStatus;

}
