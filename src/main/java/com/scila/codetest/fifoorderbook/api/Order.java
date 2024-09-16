package com.scila.codetest.fifoorderbook.api;

import com.scila.codetest.fifoorderbook.enums.OrderOperation;
import com.scila.codetest.fifoorderbook.enums.OrderSide;

public interface Order {

    /**
     * The timestamp when the order was created.
     */
    long timeStampMs();

    /**
     * The orderid is used as an identifier of the Order lifecycle e.g. Add - Update - Cancel.
     */
    String orderId();

    /**
     * The price the order will match at.
     */
    double price();

    /**
     * The initial volume of the order at inserts.
     */
    double initialVolume();

    /**
     * The current volume after matching.
     */
    double currentVolume();

    /**
     * The order side of the order e.g. Bid/Ask.
     */
    OrderSide orderSide();

    /**
     * The order operation is Add, Update or Cancel.
     */
    OrderOperation orderOperation();
}
