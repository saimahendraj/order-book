package com.smj.order.adapter;

import com.smj.order.adapter.entities.ExecutionEntity;
import com.smj.order.adapter.entities.OrderEntity;
import com.smj.order.resources.model.Execution;
import com.smj.order.resources.model.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RepositoryMapper {

    public List<Order> getOrders(List<OrderEntity> orderEntities) {
        return orderEntities.stream().map(this::getOrder).collect(Collectors.toList());
    }

    private Order getOrder(OrderEntity orderEntity) {
        return Order.builder()
                .orderId(orderEntity.getOrderId())
                .entryDate(orderEntity.getEntryDate())
                .orderQuantity(orderEntity.getOrderQuantity())
                .orderType(orderEntity.getOrderType())
                .price(orderEntity.getPrice())
                .instrumentId(orderEntity.getInstrumentId())
                .build();
    }

    public List<Execution> getExecution(List<ExecutionEntity> executionEntities) {
        return executionEntities.stream()
                .map(this::getExecution)
                .collect(Collectors.toList());
    }

    private Execution getExecution(ExecutionEntity executionEntity) {
        return Execution.builder()
                .price(executionEntity.getPrice())
                .quantity(executionEntity.getQuantity())
                .instrumentId(executionEntity.getInstrumentId())
                .build();
    }

    public List<OrderEntity> getOrdersEntities(Long instrumentId, List<Order> orders) {
        return Optional.ofNullable(orders)
                .map(orderStr -> orderStr.stream()
                        .map(order -> OrderEntity.builder()
                                .instrumentId(instrumentId)
                                .orderBookId(order.getOrderBookId())
                                .entryDate(order.getEntryDate())
                                .orderQuantity(order.getOrderQuantity())
                                .price(order.getPrice())
                                .orderType(order.getOrderType())
                                .build())
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public List<ExecutionEntity> getExecutionEntities(Long instrumentId, List<Execution> executions) {
        return Optional.ofNullable(executions)
                .map(executionStr -> executionStr.stream()
                        .map(execution -> ExecutionEntity.builder()
                                .instrumentId(instrumentId)
                                .price(execution.getPrice())
                                .quantity(execution.getQuantity())
                                .build())
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
