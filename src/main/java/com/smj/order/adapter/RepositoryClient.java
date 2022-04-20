package com.smj.order.adapter;

import com.smj.order.adapter.entities.ExecutionEntity;
import com.smj.order.adapter.entities.OrderBookEntity;
import com.smj.order.adapter.entities.OrderEntity;
import com.smj.order.order_book.OrderBook;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RepositoryClient {
    
    private final ExecutionJpa executionJpa;
    
    private final OrderBookJpa orderBookJpa;
    
    private final OrderJpa orderJpa;
    
    private final RepositoryMapper repositoryMapper;

    public OrderBook fetchOrderBook(Long instrumentId) {
        List<ExecutionEntity> executionEntities = executionJpa.findByInstrumentId(instrumentId);
        List<OrderEntity> orderEntities = orderJpa.findByInstrumentId(instrumentId);
        OrderBookEntity orderBookEntity = orderBookJpa.findByInstrumentId(instrumentId);
        if (null != orderBookEntity) {
            return OrderBook.builder()
                    .orderBookId(orderBookEntity.getOrderBookId())
                    .orderBookStatus(orderBookEntity.getOrderBookStatus())
                    .instrumentId(instrumentId)
                    .executions(repositoryMapper.getExecution(executionEntities))
                    .orders(repositoryMapper.getOrders(orderEntities)).build();
        }
        return null;
    }

    public void saveOrderBook(Long instrumentId, OrderBook orderBook) {
        orderBookJpa.save(OrderBookEntity.builder().orderBookStatus(orderBook.getOrderBookStatus()).instrumentId(orderBook.getInstrumentId()).build());
        List<OrderEntity> ordersEntities = repositoryMapper.getOrdersEntities(instrumentId, orderBook.getOrders());
        if (!ordersEntities.isEmpty()) {
            orderJpa.saveAll(ordersEntities);
        }
        List<ExecutionEntity> executionEntities = repositoryMapper.getExecutionEntities(instrumentId, orderBook.getExecutions());
        if (!executionEntities.isEmpty()) {
            executionJpa.saveAll(executionEntities);
        }
    }
}
