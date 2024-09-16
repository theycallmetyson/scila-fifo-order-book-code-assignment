package com.scila.codetest.fifoorderbook;


import com.scila.codetest.fifoorderbook.api.Order;
import com.scila.codetest.fifoorderbook.enums.OrderOperation;
import com.scila.codetest.fifoorderbook.enums.OrderSide;
import com.scila.codetest.fifoorderbook.model.SimpleOrder;

public class FifoOrderbookTestUtils {

    public static Order buildAddOrder(String orderId, double price, double volume, OrderSide orderSide, long timeStampMs) {
        return new SimpleOrder(timeStampMs, orderId, price, volume, volume, orderSide, OrderOperation.ADD);
    }

    public static Order buildUpdateOrder(String orderId, double price, double initialVolume, double volume, OrderSide orderSide, long timeStampMs) {
        return new SimpleOrder(timeStampMs, orderId, price, initialVolume, volume, orderSide, OrderOperation.UPDATE);
    }

    public static Order buildCancelOrder(String orderId, double price, double volume, OrderSide orderSide, long timeStampMs) {
        return new SimpleOrder(timeStampMs, orderId, price, volume, volume, orderSide, OrderOperation.CANCEL);
    }
}
