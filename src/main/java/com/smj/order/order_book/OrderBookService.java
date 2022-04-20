package com.smj.order.order_book;

import com.smj.order.resources.model.Execution;
import com.smj.order.resources.model.Instrument;
import com.smj.order.resources.model.Order;
import com.smj.order.resources.model.Response;

public interface OrderBookService {
    Response manageOrderBook(Instrument instrument);

    Response addOrder(Order order);

    Response addExecution(Execution execution);
}
