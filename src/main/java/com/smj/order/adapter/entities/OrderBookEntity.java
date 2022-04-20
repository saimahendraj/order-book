package com.smj.order.adapter.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDER_BOOK")
public class OrderBookEntity {
    @Id
    @Column(name = "order_book_id")
    @GeneratedValue
    private Long orderBookId;

    @Column
    private Long instrumentId;

    @Column
    private String orderBookStatus;

}
