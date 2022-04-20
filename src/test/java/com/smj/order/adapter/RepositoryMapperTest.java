package com.smj.order.adapter;

import com.smj.order.adapter.entities.ExecutionEntity;
import com.smj.order.adapter.entities.OrderEntity;
import com.smj.order.resources.model.Execution;
import com.smj.order.resources.model.Order;
import com.smj.order.util.DataPopulator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RepositoryMapperTest extends DataPopulator {

    @InjectMocks
    RepositoryMapper repositoryMapper;

    @Test
    void getOrders() {
        //Given
        List<OrderEntity> orderEntities = Collections.singletonList(getOrderEntity());
        //When
        List<Order> orders = repositoryMapper.getOrders(orderEntities);
        //Then
        assertEquals(orderEntities.get(0).getOrderId(), orders.get(0).getOrderId());
    }

    @Test
    void testShouldGetExecutionObject() {
        //Given
        List<ExecutionEntity> executionEntities = Collections.singletonList(getExecutionEntity());
        //When
        List<Execution> execution = repositoryMapper.getExecution(executionEntities);
        //Then
        assertEquals(executionEntities.get(0).getInstrumentId(), execution.get(0).getInstrumentId());
    }

    @Test
    void getOrdersEntities() {
        //Given
        List<Order> orders = Collections.singletonList(getOrder());
        //When
        List<OrderEntity> ordersEntities = repositoryMapper.getOrdersEntities(123L, orders);
        //Then
        assertEquals(orders.get(0).getOrderId(), ordersEntities.get(0).getOrderId());
    }

    @Test
    void getExecutionEntities() {
        //Given
        List<Execution> executions = Collections.singletonList(getExecution());
        //When
        List<ExecutionEntity> executionEntities = repositoryMapper.getExecutionEntities(135L, executions);
        //Then
        assertEquals(executions.get(0).getInstrumentId(), executionEntities.get(0).getInstrumentId());
    }
}
