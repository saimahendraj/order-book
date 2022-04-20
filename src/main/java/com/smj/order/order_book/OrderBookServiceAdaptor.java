package com.smj.order.order_book;

import com.smj.order.adapter.RepositoryClient;
import com.smj.order.exception.CustomException;
import com.smj.order.resources.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class OrderBookServiceAdaptor implements OrderBookService {
    private static final String OPEN="Open";
    private static final String CLOSE="close";
    private static final String SUCCESS = "SUCCESS";
    private static final String NOT_FOUND = "Order book is not found";
    private static final String CLIENT_ORDER_BOOK = "repositoryClient Order Book:{}";
    private static final String ORDER_BOOK = "Order Book:{}";
    private static final String ORDER_BOOK_IS_OPENED = "Order Book is opened";
    private static final String ORDER_BOOK_IS_CLOSED = "Order Book is closed";
    private final RepositoryClient repositoryClient;

    @Override
    public Response manageOrderBook(Instrument instrument) {

        OrderBook orderBook = OrderBook.builder().build();
        OrderBook fetchOrderBook = repositoryClient.fetchOrderBook(instrument.getInstrumentId());
        if (fetchOrderBook != null) {
            fetchOrderBook.setOrderBookStatus(instrument.getOrderBookStatus());
            log.info(CLIENT_ORDER_BOOK, fetchOrderBook);
            orderBook = fetchOrderBook;
        } else {
            orderBook.setOrderBookStatus(instrument.getOrderBookStatus());
            orderBook.setInstrumentId(instrument.getInstrumentId());
            orderBook.setOrders(new ArrayList<>());
            orderBook.setExecutions(new ArrayList<>());
            log.info("new OrderBook::{}", orderBook);
            repositoryClient.saveOrderBook(instrument.getInstrumentId(), orderBook);
        }
        Status status = Status.builder()
                .statusCode(HttpStatus.OK.value())
                .message(SUCCESS)
                .build();
        return Response.builder()
                .status(status)
                .orderBook(orderBook)
                .build();
    }

    @Override
    public Response addOrder(Order order) {
        OrderBook orderBook = repositoryClient.fetchOrderBook(order.getInstrumentId());
        log.info(CLIENT_ORDER_BOOK, orderBook);
        if (orderBook == null)
            throw new CustomException(HttpStatus.BAD_REQUEST, NOT_FOUND);
        List<Order> orders = orderBook.getOrders();
        String orderBookStatus = orderBook.getOrderBookStatus();
        Status status = Status.builder().statusCode(HttpStatus.OK.value()).build();
        if (StringUtils.hasText(orderBookStatus)) {
            if (OPEN.equalsIgnoreCase(orderBookStatus)) {
                log.info(ORDER_BOOK_IS_OPENED);
                order.setOrderBookId(orderBook.getOrderBookId());
                orders.add(order);
                orderBook.setOrders(orders);
                status.setMessage(SUCCESS);
                log.info(ORDER_BOOK, orderBook);
                repositoryClient.saveOrderBook(order.getInstrumentId(), orderBook);
            } else {
                log.info(ORDER_BOOK_IS_CLOSED);
                status.setMessage("Order book is closed, not allowed to add order");
                log.info(ORDER_BOOK, orderBook);
            }
        } else {
            status.setStatusCode(HttpStatus.NOT_FOUND.value());
            status.setMessage(NOT_FOUND);
        }
        return Response.builder()
                .status(status)
                .orderBook(orderBook)
                .build();
    }

    @Override
    public Response addExecution(Execution execution) {
        OrderBook orderBook = repositoryClient.fetchOrderBook(execution.getInstrumentId());
        log.info(CLIENT_ORDER_BOOK, orderBook);
        if (orderBook == null)
            throw new CustomException(HttpStatus.BAD_REQUEST, NOT_FOUND);
        List<Execution> executions = orderBook.getExecutions();
        String orderBookStatus = orderBook.getOrderBookStatus();
        Status status = Status.builder().statusCode(HttpStatus.OK.value()).build();
        if (StringUtils.hasText(orderBookStatus)) {
            if (CLOSE.equalsIgnoreCase(orderBookStatus)) {
                log.info(ORDER_BOOK_IS_CLOSED);
                executions.add(execution);
                orderBook.setExecutions(executions);
                status.setMessage(SUCCESS);
                log.info(ORDER_BOOK, orderBook);
                repositoryClient.saveOrderBook(execution.getInstrumentId(), orderBook);
            } else {
                log.info(ORDER_BOOK_IS_OPENED);
                status.setMessage("Order book is opened, not allowed to add execution");
                log.info(ORDER_BOOK, orderBook);
            }
        } else {
            status.setStatusCode(HttpStatus.NOT_FOUND.value());
            status.setMessage(NOT_FOUND);
        }
        return Response.builder()
                .status(status)
                .orderBook(orderBook)
                .build();
    }
}
