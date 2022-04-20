package com.smj.order.adapter;

import com.smj.order.adapter.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJpa extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByInstrumentId(Long instrumentId);
}
