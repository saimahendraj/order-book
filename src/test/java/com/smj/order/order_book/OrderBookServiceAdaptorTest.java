package com.smj.order.order_book;

import com.smj.order.adapter.RepositoryClient;
import com.smj.order.exception.CustomException;
import com.smj.order.resources.model.Execution;
import com.smj.order.resources.model.Instrument;
import com.smj.order.resources.model.Order;
import com.smj.order.resources.model.Response;
import com.smj.order.util.DataPopulator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderBookServiceAdaptorTest extends DataPopulator {

    @Mock
    private RepositoryClient repositoryClient;

    @InjectMocks
    private OrderBookServiceAdaptor orderBookServiceAdaptor;

    @Test
    void manageOrderBookWithNoExistingOrderBook() {
        //Given
        Instrument instrument = getInstrument();
        when(repositoryClient.fetchOrderBook(instrument.getInstrumentId())).thenReturn(null);
        //When
        Response response = orderBookServiceAdaptor.manageOrderBook(instrument);
        //Then
        assertNotNull(response);
        assertNotNull(response.getStatus());
        assertNotNull(response.getOrderBook());
    }

    @Test
    void manageOrderBookWithExistingOrderBook() {
        //Given
        Instrument instrument = getInstrument();
        when(repositoryClient.fetchOrderBook(instrument.getInstrumentId())).thenReturn(getOrderBook());
        //When
        Response response = orderBookServiceAdaptor.manageOrderBook(instrument);
        //Then
        assertNotNull(response);
        assertNotNull(response.getStatus());
        assertNotNull(response.getOrderBook());
    }

    @Test
    void addOrderWithNoExistingOrderBook() {
        //Given
        Order order = getOrder();
        when(repositoryClient.fetchOrderBook(order.getInstrumentId())).thenReturn(null);
        //When && Then
        assertThrows(CustomException.class, () -> orderBookServiceAdaptor.addOrder(order));
    }

    @Test
    void addOrderWithExistingOrderBookAndEmptyStatus() {
        //Given
        Order order = getOrder();
        OrderBook orderBook = getOrderBook();
        orderBook.setOrderBookStatus("");
        when(repositoryClient.fetchOrderBook(order.getInstrumentId())).thenReturn(orderBook);
        //When
        Response response = orderBookServiceAdaptor.addOrder(order);
        //Then
        assertEquals(404, response.getStatus().getStatusCode());
    }

    @Test
    void addOrderWithExistingOrderBookAndClosedStatus() {
        //Given
        Order order = getOrder();
        OrderBook orderBook = getOrderBook();
        orderBook.setOrderBookStatus("CLOSE");
        when(repositoryClient.fetchOrderBook(order.getInstrumentId())).thenReturn(orderBook);
        //When
        Response response = orderBookServiceAdaptor.addOrder(order);
        //Then
        assertEquals(200, response.getStatus().getStatusCode());
        assertEquals("Order book is closed, not allowed to add order", response.getStatus().getMessage());
    }

    @Test
    void addOrderWithExistingOrderBookAndOpenStatus() {
        //Given
        Order order = getOrder();
        OrderBook orderBook = getOrderBook();
        when(repositoryClient.fetchOrderBook(order.getInstrumentId())).thenReturn(orderBook);
        //When
        Response response = orderBookServiceAdaptor.addOrder(order);
        //Then
        assertEquals(200, response.getStatus().getStatusCode());
        assertEquals("SUCCESS", response.getStatus().getMessage());
    }

    @Test
    void addExecutionWithNoExistingOrderBook() {
        //Given
        Execution execution = getExecution();
        when(repositoryClient.fetchOrderBook(execution.getInstrumentId())).thenReturn(null);
        //When && Then
        assertThrows(CustomException.class, () -> orderBookServiceAdaptor.addExecution(execution));
    }

    @Test
    void addExecutionWithExistingOrderBookAndEmptyStatus() {
        //Given
        Execution execution = getExecution();
        OrderBook orderBook = getOrderBook();
        orderBook.setOrderBookStatus("");
        when(repositoryClient.fetchOrderBook(execution.getInstrumentId())).thenReturn(orderBook);
        //When
        Response response = orderBookServiceAdaptor.addExecution(execution);
        //Then
        assertEquals(404, response.getStatus().getStatusCode());
    }

    @Test
    void addExecutionWithExistingOrderBookAndOpenStatus() {
        //Given
        Execution execution = getExecution();
        OrderBook orderBook = getOrderBook();
        when(repositoryClient.fetchOrderBook(execution.getInstrumentId())).thenReturn(orderBook);
        //When
        Response response = orderBookServiceAdaptor.addExecution(execution);
        //Then
        assertEquals(200, response.getStatus().getStatusCode());
        assertEquals("Order book is opened, not allowed to add execution", response.getStatus().getMessage());
    }

    @Test
    void addExecutionWithExistingOrderBookAndCloseStatus() {
        //Given
        Execution execution = getExecution();
        OrderBook orderBook = getOrderBook();
        orderBook.setOrderBookStatus("CLOSE");
        when(repositoryClient.fetchOrderBook(execution.getInstrumentId())).thenReturn(orderBook);
        //When
        Response response = orderBookServiceAdaptor.addExecution(execution);
        //Then
        assertEquals(200, response.getStatus().getStatusCode());
        assertEquals("SUCCESS", response.getStatus().getMessage());
    }
}
