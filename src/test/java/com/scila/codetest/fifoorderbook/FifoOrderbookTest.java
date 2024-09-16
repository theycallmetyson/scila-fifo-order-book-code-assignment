package com.scila.codetest.fifoorderbook;

import com.scila.codetest.fifoorderbook.api.Orderbook;
import com.scila.codetest.fifoorderbook.enums.OrderSide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.scila.codetest.fifoorderbook.FifoOrderbookTestUtils.buildAddOrder;
import static com.scila.codetest.fifoorderbook.FifoOrderbookTestUtils.buildCancelOrder;
import static com.scila.codetest.fifoorderbook.FifoOrderbookTestUtils.buildUpdateOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;


class FifoOrderbookTest {
    private Orderbook orderbook;

    @BeforeEach
    void init() {
        this.orderbook = new FifoOrderbook("");
    }

    @Test
    void addOrderToOrderbook() {
        orderbook.updateOrderbook(buildAddOrder("1", 100, 10, OrderSide.BID, 1));
        orderbook.updateOrderbook(buildAddOrder("2", 100, 10, OrderSide.BID, 2));
        orderbook.updateOrderbook(buildAddOrder("3", 101, 10, OrderSide.ASK, 3));

        assertEquals(3, orderbook.totalNumberOfActiveOrders());
        assertEquals(2, orderbook.totalNumberOfBidOrders());
        assertEquals(1, orderbook.totalNumberOfAskOrders());
    }

    @Test
    void updateOrder() {
        orderbook.updateOrderbook(buildAddOrder("1", 100, 10, OrderSide.ASK, 1));
        assertEquals(100, orderbook.getBestAskOrder().get().price());
        orderbook.updateOrderbook(buildUpdateOrder("1", 99, 10, 5, OrderSide.ASK, 2));
        assertEquals(99, orderbook.getBestAskOrder().get().price());
        assertEquals(1, orderbook.totalNumberOfAskPriceLevels());
    }

    @Test
    void updateOrderInOrderbook() {
        orderbook.updateOrderbook(buildAddOrder("1", 100, 10, OrderSide.BID, 1));
        assertEquals(1, orderbook.totalNumberOfPriceLevels());
        assertEquals(1, orderbook.totalNumberOfBidPriceLevels());
        assertEquals(0, orderbook.totalNumberOfAskPriceLevels());
        assertEquals(10, orderbook.getOrder("1").currentVolume());
        assertEquals(100, orderbook.getOrder("1").price());
        assertEquals(10, orderbook.totalBidVolume());
        assertEquals(0, orderbook.totalAskVolume());
        assertEquals(10, orderbook.totalOrderVolume());

        orderbook.updateOrderbook(buildUpdateOrder("1", 99, 10, 9, OrderSide.BID, 2));
        assertEquals(1, orderbook.totalNumberOfPriceLevels());
        assertEquals(1, orderbook.totalNumberOfBidPriceLevels());
        assertEquals(0, orderbook.totalNumberOfAskPriceLevels());
        assertEquals(9, orderbook.getOrder("1").currentVolume());
        assertEquals(99, orderbook.getOrder("1").price());
        assertEquals(9, orderbook.totalBidVolume());
        assertEquals(0, orderbook.totalAskVolume());
        assertEquals(9, orderbook.totalOrderVolume());
    }

    @Test
    void bestPriceAfterAddOrders() {
        orderbook.updateOrderbook(buildAddOrder("1", 100, 10, OrderSide.BID, 1));
        orderbook.updateOrderbook(buildAddOrder("2", 102, 10, OrderSide.ASK, 2));
        assertEquals(100, orderbook.getBestBidPrice());
        assertEquals(102, orderbook.getBestAskPrice());

        orderbook.updateOrderbook(buildAddOrder("3", 100.5, 10, OrderSide.BID, 3));
        orderbook.updateOrderbook(buildAddOrder("4", 103, 10, OrderSide.ASK, 3));
        assertEquals(100.5, orderbook.getBestBidPrice());
        assertEquals(102, orderbook.getBestAskPrice());
    }

    @Test
    void cancelOrdersInOrderbook() {
        orderbook.updateOrderbook(buildAddOrder("1", 100, 10, OrderSide.BID, 1));
        orderbook.updateOrderbook(buildAddOrder("2", 100, 10, OrderSide.BID, 2));
        orderbook.updateOrderbook(buildAddOrder("3", 101, 10, OrderSide.ASK, 3));

        assertEquals(3, orderbook.totalNumberOfActiveOrders());
        assertEquals(2, orderbook.totalNumberOfBidOrders());
        assertEquals(1, orderbook.totalNumberOfAskOrders());

        orderbook.updateOrderbook(buildCancelOrder("1", 100, 10, OrderSide.BID, 4));
        orderbook.updateOrderbook(buildCancelOrder("3", 101, 10, OrderSide.ASK, 5));
        assertEquals(1, orderbook.totalNumberOfActiveOrders());
        assertEquals(1, orderbook.totalNumberOfBidOrders());
        assertEquals(0, orderbook.totalNumberOfAskOrders());
    }

    @Test
    void bestPriceAfterCancelOrders() {
        orderbook.updateOrderbook(buildAddOrder("1", 99, 10, OrderSide.BID, 1));
        orderbook.updateOrderbook(buildAddOrder("2", 100, 10, OrderSide.BID, 2));
        orderbook.updateOrderbook(buildAddOrder("3", 101, 10, OrderSide.ASK, 3));
        orderbook.updateOrderbook(buildAddOrder("4", 103, 10, OrderSide.ASK, 3));
        assertEquals(100, orderbook.getBestBidPrice());
        assertEquals(101, orderbook.getBestAskPrice());

        orderbook.updateOrderbook(buildCancelOrder("2", 100, 10, OrderSide.BID, 4));
        orderbook.updateOrderbook(buildCancelOrder("3", 101, 10, OrderSide.ASK, 5));
        assertEquals(99, orderbook.getBestBidPrice());
        assertEquals(103, orderbook.getBestAskPrice());
    }

    @Test
    void volumeInOrderbook() {
        orderbook.updateOrderbook(buildAddOrder("1", 100, 11, OrderSide.BID, 1));
        orderbook.updateOrderbook(buildAddOrder("2", 102, 13, OrderSide.ASK, 2));
        orderbook.updateOrderbook(buildAddOrder("3", 99, 12, OrderSide.BID, 3));
        orderbook.updateOrderbook(buildAddOrder("4", 103, 10, OrderSide.ASK, 4));
        assertEquals(46, orderbook.totalOrderVolume());
        assertEquals(23, orderbook.totalBidVolume());
        assertEquals(23, orderbook.totalAskVolume());


        orderbook.updateOrderbook(buildCancelOrder("1", 100, 11, OrderSide.BID, 5));
        orderbook.updateOrderbook(buildCancelOrder("2", 102, 13, OrderSide.ASK, 6));
        assertEquals(22, orderbook.totalOrderVolume());
        assertEquals(12, orderbook.totalBidVolume());
        assertEquals(10, orderbook.totalAskVolume());
    }

    @Test
    void volumeAtPriceLevel() {
        orderbook.updateOrderbook(buildAddOrder("1", 100, 11, OrderSide.BID, 1));
        orderbook.updateOrderbook(buildAddOrder("2", 102, 13, OrderSide.ASK, 2));
        orderbook.updateOrderbook(buildAddOrder("3", 99, 12, OrderSide.BID, 3));
        orderbook.updateOrderbook(buildAddOrder("4", 103, 10, OrderSide.ASK, 4));
        orderbook.updateOrderbook(buildAddOrder("5", 100, 10, OrderSide.BID, 5));
        assertEquals(34, orderbook.totalVolumeAtPriceLevel(1));
        assertEquals(21, orderbook.totalBidVolumeAtPriceLevel(1));
        assertEquals(13, orderbook.totalAskVolumeAtPriceLevel(1));
        assertEquals(22, orderbook.totalVolumeAtPriceLevel(2));
        assertEquals(12, orderbook.totalBidVolumeAtPriceLevel(2));
        assertEquals(10, orderbook.totalAskVolumeAtPriceLevel(2));

        orderbook.updateOrderbook(buildCancelOrder("1", 100, 11, OrderSide.BID, 5));
        orderbook.updateOrderbook(buildCancelOrder("2", 102, 13, OrderSide.ASK, 6));
        assertEquals(20, orderbook.totalVolumeAtPriceLevel(1));
        assertEquals(10, orderbook.totalBidVolumeAtPriceLevel(1));
        assertEquals(10, orderbook.totalAskVolumeAtPriceLevel(1));
        assertEquals(12, orderbook.totalVolumeAtPriceLevel(2));
        assertEquals(12, orderbook.totalBidVolumeAtPriceLevel(2));
        assertEquals(0, orderbook.totalAskVolumeAtPriceLevel(2));
    }

    @Test
    void priceAtPriceLevel() {
        orderbook.updateOrderbook(buildAddOrder("1", 100, 11, OrderSide.BID, 1));
        orderbook.updateOrderbook(buildAddOrder("2", 102, 13, OrderSide.ASK, 2));
        orderbook.updateOrderbook(buildAddOrder("3", 99, 12, OrderSide.BID, 3));
        orderbook.updateOrderbook(buildAddOrder("4", 103, 10, OrderSide.ASK, 4));
        orderbook.updateOrderbook(buildAddOrder("5", 100, 10, OrderSide.BID, 5));
        assertEquals(100, orderbook.getBidPriceAtPriceLevel(1));
        assertEquals(102, orderbook.getAskPriceAtPriceLevel(1));
        assertEquals(99, orderbook.getBidPriceAtPriceLevel(2));
        assertEquals(103, orderbook.getAskPriceAtPriceLevel(2));

        orderbook.updateOrderbook(buildCancelOrder("1", 100, 11, OrderSide.BID, 5));
        orderbook.updateOrderbook(buildCancelOrder("2", 102, 13, OrderSide.ASK, 6));
        assertEquals(100, orderbook.getBidPriceAtPriceLevel(1));
        assertEquals(103, orderbook.getAskPriceAtPriceLevel(1));
        assertEquals(99, orderbook.getBidPriceAtPriceLevel(2));
        assertEquals(0, orderbook.getAskPriceAtPriceLevel(2));
    }

    @Test
    void firstInFirstOut() {
        orderbook.updateOrderbook(buildAddOrder("0", 99, 11, OrderSide.BID, 1));
        orderbook.updateOrderbook(buildAddOrder("1", 100, 11, OrderSide.BID, 1));
        orderbook.updateOrderbook(buildAddOrder("2", 102, 13, OrderSide.ASK, 2));
        orderbook.updateOrderbook(buildAddOrder("3", 99, 12, OrderSide.BID, 3));
        orderbook.updateOrderbook(buildAddOrder("4", 101, 10, OrderSide.ASK, 4));
        orderbook.updateOrderbook(buildAddOrder("5", 100, 10, OrderSide.BID, 5));
        assertEquals("1", orderbook.getBestBidOrder().get().orderId());
        assertEquals("4", orderbook.getBestAskOrder().get().orderId());
    }
}