package com.scila.codetest.fifoorderbook.model;

import com.scila.codetest.fifoorderbook.api.Order;
import com.scila.codetest.fifoorderbook.enums.OrderOperation;
import com.scila.codetest.fifoorderbook.enums.OrderSide;

public record SimpleOrder(long timeStampMs,
                          String orderId,
                          double price,
                          double initialVolume,
                          double currentVolume,
                          OrderSide orderSide,
                          OrderOperation orderOperation) implements Order {
}
