package com.smj.order.adapter;

import com.smj.order.adapter.entities.OrderBookEntity;
import com.smj.order.order_book.OrderBook;
import com.smj.order.util.DataPopulator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RepositoryClientTest extends DataPopulator {
    @Mock
    private ExecutionJpa executionJpa;
    @Mock
    private OrderBookJpa orderBookJpa;
    @Mock
    private OrderJpa orderJpa;
    @Mock
    private RepositoryMapper repositoryMapper;
    @InjectMocks
    private RepositoryClient repositoryClient;
    @Test
    void fetchOrderBookWithOutOrderBooks() {
        //Given
        when(orderBookJpa.findByInstrumentId(anyLong())).thenReturn(null);
        //When
        OrderBook orderBook = repositoryClient.fetchOrderBook(123L);
        //Then
        assertNull(orderBook);
    }

    @Test
    void fetchOrderBook() {
        //Given
        when(orderBookJpa.findByInstrumentId(anyLong())).thenReturn(OrderBookEntity.builder().orderBookId(123L).orderBookStatus("OPEN").instrumentId(123L).build());
        //When
        OrderBook orderBook = repositoryClient.fetchOrderBook(123L);
        //Then
        assertNotNull(orderBook);
    }

    @Test
    void saveOrderBook() {
        repositoryClient.saveOrderBook(123L, OrderBook.builder().orderBookStatus("OPEN").instrumentId(123L).build());
    }

    @Test
    void saveOrderBookWithOrders() {
        when(repositoryMapper.getOrdersEntities(any(), any())).thenCallRealMethod();
        repositoryClient.saveOrderBook(123L, OrderBook.builder().orderBookStatus("OPEN").instrumentId(123L).orders(Collections.singletonList(getOrder())).build());
    }

    @Test
    void saveOrderBookWithExecutions() {
        when(repositoryMapper.getExecutionEntities(any(), any())).thenCallRealMethod();
        repositoryClient.saveOrderBook(123L, OrderBook.builder().orderBookStatus("OPEN").instrumentId(123L).executions(Collections.singletonList(getExecution())).build());
    }
}
