package com.smj.order.adapter;

import com.smj.order.adapter.entities.ExecutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionJpa extends JpaRepository<ExecutionEntity, Long> {
    List<ExecutionEntity> findByInstrumentId(Long instrumentId);

}
