package com.smj.order.resources;

import com.smj.order.adapter.entities.OrderBookEntity;
import com.smj.order.adapter.OrderBookJpa;
import com.smj.order.order_book.OrderBook;
import com.smj.order.resources.model.Execution;
import com.smj.order.resources.model.Instrument;
import com.smj.order.resources.model.Order;
import com.smj.order.util.DataPopulator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerIntegrationTest extends DataPopulator {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderBookJpa orderBookJpa;

    @Test
    void testShouldOpenOrderBookSuccessfully() throws Exception {
        Instrument instrument = getInstrument();
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orderbook",instrument)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(instrument));
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertNotNull(mvcResult);
        assertEquals(200, mvcResult.getResponse().getStatus());

    }

    @Test
    @Transactional
    void testShouldAddOrderToOrderBookSuccessfully() throws Exception {
        Order order = getOrder();
        OrderBook orderBook = getOrderBook();
        orderBookJpa.save(OrderBookEntity.builder().orderBookStatus(orderBook.getOrderBookStatus()).instrumentId(orderBook.getInstrumentId()).build());
        String jsonToString = jsonToString(order);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orderbook/order",order)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertNotNull(mvcResult);
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    @Transactional
    void testShouldAddExecutionToOrderBookSuccessfully() throws Exception {
        Execution execution = getExecution();
        OrderBook orderBook = getOrderBook();
        orderBook.setOrderBookStatus("CLOSED");
        orderBookJpa.save(OrderBookEntity.builder().orderBookStatus(orderBook.getOrderBookStatus()).instrumentId(orderBook.getInstrumentId()).build());
        String jsonToString = jsonToString(execution);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orderbook/order/execution",execution)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertNotNull(mvcResult);
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void testShouldFailToAddOrderToOrderBookDueToMissingOrderBook() throws Exception {
        Order order = getOrder();
        String jsonToString = jsonToString(order);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orderbook/order",order)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertNotNull(mvcResult);
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    @Transactional
    void testShouldFailToAddOrderToOrderBookDueToClosedOrderBook() throws Exception {
        Order order = getOrder();
        OrderBook orderBook = getOrderBook();
        orderBook.setOrderBookStatus("CLOSED");
        orderBookJpa.save(OrderBookEntity.builder().orderBookStatus(orderBook.getOrderBookStatus()).instrumentId(orderBook.getInstrumentId()).build());
        String jsonToString = jsonToString(order);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orderbook/order",order)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertNotNull(mvcResult);
        assertEquals(200, mvcResult.getResponse().getStatus());
    }


}
