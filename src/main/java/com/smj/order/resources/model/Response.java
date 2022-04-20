package com.smj.order.resources.model;

import com.smj.order.order_book.OrderBook;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private Status status;
    private OrderBook orderBook;
}
