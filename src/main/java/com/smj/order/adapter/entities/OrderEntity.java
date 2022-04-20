package com.smj.order.adapter.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDERS")
public class OrderEntity {

    @Id
    @Column(name = "order_id")
    @GeneratedValue
    private Long orderId;

    @Column
    private Long orderBookId;

    @Column
    private Long orderQuantity;

    @Column
    private double price;

    @Column
    private LocalDateTime entryDate;

    @Column
    private Long instrumentId;

    @Column
    private String orderType;

}
