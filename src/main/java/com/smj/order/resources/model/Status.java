package com.smj.order.resources.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Status {
    private int statusCode;
    private String message;
}
