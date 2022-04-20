package com.smj.order.resources;

import com.smj.order.order_book.OrderBookService;
import com.smj.order.resources.model.Execution;
import com.smj.order.resources.model.Instrument;
import com.smj.order.resources.model.Order;
import com.smj.order.resources.model.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderBookHandler {

    private final OrderBookService orderBookService;

    public Response manageOrderBook(Instrument instrument) {
        return orderBookService.manageOrderBook(instrument);
    }

    public Response addOrder(Order order) {
        return orderBookService.addOrder(order);
    }

    public Response addExecution(Execution execution) {
        return orderBookService.addExecution(execution);
    }
}
