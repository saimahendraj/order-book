package com.smj.order.adapter;

import com.smj.order.adapter.entities.OrderBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBookJpa extends JpaRepository<OrderBookEntity, Long> {
    OrderBookEntity findByInstrumentId(Long instrumentId);
}
