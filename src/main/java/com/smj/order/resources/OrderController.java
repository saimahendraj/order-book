package com.smj.order.resources;

import com.smj.order.resources.model.Execution;
import com.smj.order.resources.model.Instrument;
import com.smj.order.resources.model.Order;
import com.smj.order.resources.model.Response;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Slf4j
@RestController
@AllArgsConstructor
@Validated
public class OrderController {
    
    private OrderBookHandler orderBookHandler;

    @ApiResponses(value = {
            @ApiResponse(code=200,message = "SUCCESS"),
            @ApiResponse(code=400,message = "Bad Request"),
            @ApiResponse(code=500,message = "Internal Server Error"),
    })
    @ApiOperation(value = "Manage(Open/close) an order book",notes = "Please pass instrumentId and status as open or close to Order Book")
    @PostMapping("/orderbook")
    public Response manageOrderBook(@RequestBody @Valid Instrument instrument){
        log.info("OrderBookController instrument:{}",instrument);
        return orderBookHandler.manageOrderBook(instrument);
    }

    @ApiOperation(value = "add new Market/Limit order to Order Book",notes = "Please pass instrumentId and Order details to add to Order Book")
    @ApiResponses(value = {
            @ApiResponse(code=200,message = "SUCCESS"),
            @ApiResponse(code=400,message = "Bad Request"),
            @ApiResponse(code=500,message = "Internal Server Error"),
    })
    @PostMapping("/orderbook/order")
    public Response addOrder(@RequestBody @Valid Order order){
        log.info("OrderBookController Order:{}",order);
        return orderBookHandler.addOrder(order);
    }

    @ApiOperation(value = "add an execution to Order Book",notes = "Please pass instrumentId and quantity and price to add to Order Book execution list")
    @ApiResponses(value = {
            @ApiResponse(code=200,message = "SUCCESS"),
            @ApiResponse(code=400,message = "Bad Request"),
            @ApiResponse(code=500,message = "Internal Server Error"),
    })
    @PostMapping("/orderbook/order/execution")
    public Response addExecution(@RequestBody @Valid Execution execution){
        log.info("OrderBookController execution:{}",execution);
        return orderBookHandler.addExecution(execution);
    }
}
