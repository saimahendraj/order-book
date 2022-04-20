package com.smj.order.resources;

import com.smj.order.order_book.OrderBookService;
import com.smj.order.resources.model.Execution;
import com.smj.order.resources.model.Instrument;
import com.smj.order.resources.model.Order;
import com.smj.order.resources.model.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderBookHandlerTest {

    @Mock
    private OrderBookService orderBookService;

    @InjectMocks
    private OrderBookHandler orderBookHandler;

    @Test
    public void testShouldManageOrder(){
        //Given
        Instrument instrument = Instrument.builder().build();
        Response response = Response.builder().build();
        when(orderBookService.manageOrderBook(instrument)).thenReturn(response);

        //When
        Response manageOrderBook = orderBookHandler.manageOrderBook(instrument);

        //Then
        assertEquals(response, manageOrderBook);
    }

    @Test
    public void testShouldAddOrder(){
        //Given
        Order order = Order.builder().build();
        Response response = Response.builder().build();
        when(orderBookService.addOrder(order)).thenReturn(response);

        //When
        Response addOrder = orderBookHandler.addOrder(order);

        //Then
        assertEquals(response, addOrder);
    }

    @Test
    public void testShouldAddExecution(){
        //Given
        Execution execution = Execution.builder().build();
        Response response = Response.builder().build();
        when(orderBookService.addExecution(execution)).thenReturn(response);

        //When
        Response addExecution = orderBookHandler.addExecution(execution);

        //Then
        assertEquals(response, addExecution);
    }

}
