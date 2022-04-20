package com.smj.order.order_book;

import com.smj.order.resources.model.Execution;
import com.smj.order.resources.model.Order;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderBook {

    private Long orderBookId;

    private Long instrumentId;

    private String orderBookStatus;

    private List<Order> orders;

    private List<Execution> executions;

}
