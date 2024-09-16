package com.scila.codetest.fifoorderbook.api;


import java.util.Optional;

public interface Orderbook {

    /**
     * Orderbook updates are Add, Update or Cancel orders where Update/Cancel orders are based on orderid.
     *
     * @param order that will update the orderbook.
     */
    void updateOrderbook(Order order);

    /**
     * The unique id of the orderbook, e.g. APPL.
     */
    String getOrderbookId();

    /**
     * Returns an order from the orderbook based on the orderId.
     *
     * @param orderId the orderId to be extracted.
     */
    Order getOrder(String orderId);

    /**
     * Returns the total volume in the orderbook, aggregating all volume on both the bid and ask side.
     */
    double totalOrderVolume();

    /**
     * Returns the total bid volume in the orderbook, aggregating all volume in each bid price level.
     */
    double totalBidVolume();

    /**
     * Returns the total ask volume in the orderbook, aggregating all volume in each ask price level.
     */
    double totalAskVolume();

    /**
     * Returns the highest bid price in the orderbook. If no price exists return 0.
     */
    double getBestBidPrice();

    /**
     * Returns the highest ask price in the orderbook. If no price exists return 0.
     */
    double getBestAskPrice();

    /**
     * Returns the total number of bid orders over all price levels in the orderbook.
     */
    long totalNumberOfBidOrders();

    /**
     * Returns the total number of ask orders over all price levels in the orderbook.
     */
    long totalNumberOfAskOrders();

    /**
     * Returns the total number of orders over all price levels in the orderbook aggregating both bid and ask.
     */
    long totalNumberOfActiveOrders();

    /**
     * Returns the total volume at a specific price level, aggregating bid and ask.
     */
    double totalVolumeAtPriceLevel(int priceLevel);

    /**
     * Returns the total bid volume at a specific price level. If no volume exists return 0.
     */
    double totalBidVolumeAtPriceLevel(int priceLevel);

    /**
     * Returns the total ask volume at a specific price level. If no volume exists return 0.
     */
    double totalAskVolumeAtPriceLevel(int priceLevel);

    /**
     * Returns the total number of price levels aggregating bid and ask.
     */
    int totalNumberOfPriceLevels();

    /**
     * Returns the total number of bid price levels.
     */
    int totalNumberOfBidPriceLevels();

    /**
     * Returns the total number of ask price levels.
     */
    int totalNumberOfAskPriceLevels();

    /**
     * Returns the ask price at a specific price level. If no price exists return 0.
     *
     * @param priceLevel the in the orderbook.
     */
    double getAskPriceAtPriceLevel(int priceLevel);

    /**
     * Returns the bid price at a specific price level. If no price exists return 0.
     *
     * @param priceLevel the in the orderbook.
     */
    double getBidPriceAtPriceLevel(int priceLevel);

    /**
     * Returns the best bid order.
     */
    Optional<Order> getBestBidOrder();

    /**
     * Returns the best ask order.
     */
    Optional<Order> getBestAskOrder();
}