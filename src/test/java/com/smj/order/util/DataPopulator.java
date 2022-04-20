package com.smj.order.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.smj.order.adapter.entities.ExecutionEntity;
import com.smj.order.adapter.entities.OrderEntity;
import com.smj.order.order_book.OrderBook;
import com.smj.order.resources.model.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataPopulator {

    public static Instrument getInstrument(){
        return Instrument.builder()
                .instrumentId(135L)
                .orderBookStatus("open")
                .instrumentName("financial instrument")
                .build();
    }
    public static Order getOrder(){
        return Order.builder()
                .orderBookId(1234567890L)
                .orderType("Limit order")
                .instrumentId(135L)
                .entryDate(LocalDateTime.now())
                .orderQuantity(10L)
                .price(100.00)
                .build();
    }
    public static OrderEntity getOrderEntity(){
        return OrderEntity.builder()
                .orderBookId(1234567890L)
                .orderType("Limit order")
                .instrumentId(135L)
                .entryDate(LocalDateTime.now())
                .orderQuantity(10L)
                .price(100.00)
                .build();
    }
    public static Status getStatus(){
        return Status.builder()
                .statusCode(HttpStatus.OK.value())
                .message("SUCCESS")
                .build();
    }

    public static OrderBook getOrderBook(){
        List<Order> orderList = new ArrayList<>();
        List<Execution> executionList = new ArrayList<>();
        orderList.add(getOrder());
        executionList.add(getExecution());
        return OrderBook.builder()
                .orderBookId(1234567890L)
                .orderBookStatus("open")
                .instrumentId(135L)
                .orders(orderList)
                .executions(executionList)
                .build();
    }

    public static Execution getExecution(){
        return Execution.builder()
                .instrumentId(135L)
                .quantity(10L)
                .price(200.0)
                .build();
    }

    public static ExecutionEntity getExecutionEntity(){
        return ExecutionEntity.builder()
                .instrumentId(135L)
                .quantity(10L)
                .price(200.0)
                .build();
    }

    public static Response getResult(){
        return Response.builder()
                .status(getStatus())
                .orderBook(getOrderBook())
                .build();
    }

    public  String jsonToString(Object json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
        objectMapper.registerModule(javaTimeModule);
        return objectMapper.writeValueAsString(json);
    }
}
