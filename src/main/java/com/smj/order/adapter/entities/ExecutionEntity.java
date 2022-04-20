package com.smj.order.adapter.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EXECUTION")
public class ExecutionEntity {
    @Id
    @Column(name = "execution_id")
    @GeneratedValue
    private Long executionId;
    @Column
    private Long instrumentId;
    @Column
    private Long quantity;
    @Column
    private double price;
}
