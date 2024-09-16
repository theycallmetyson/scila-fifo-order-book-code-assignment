package com.scila.codetest.fifoorderbook;

import java.util.Optional;

import com.scila.codetest.fifoorderbook.api.Order;
import com.scila.codetest.fifoorderbook.api.Orderbook;

public class FifoOrderbook implements Orderbook {

    private final String orderbookId;

    public FifoOrderbook(String orderbookId) {
        this.orderbookId = orderbookId;
    }

    @Override
    public void updateOrderbook(Order order) {

    }

    @Override
    public String getOrderbookId() {
        return orderbookId;
    }

    @Override
    public Order getOrder(String orderId) {
        return null;
    }

    @Override
    public double totalOrderVolume() {
        return 0;
    }

    @Override
    public double totalBidVolume() {
        return 0;
    }

    @Override
    public double totalAskVolume() {
        return 0;
    }

    @Override
    public double getBestBidPrice() {
        return 0;
    }

    @Override
    public double getBestAskPrice() {
        return 0;
    }

    @Override
    public long totalNumberOfBidOrders() {
        return 0;
    }

    @Override
    public long totalNumberOfAskOrders() {
        return 0;
    }

    @Override
    public long totalNumberOfActiveOrders() {
        return 0;
    }

    @Override
    public double totalVolumeAtPriceLevel(int priceLevel) {
        return 0;
    }

    @Override
    public double totalBidVolumeAtPriceLevel(int priceLevel) {
        return 0;
    }

    @Override
    public double totalAskVolumeAtPriceLevel(int priceLevel) {
        return 0;
    }

    @Override
    public int totalNumberOfPriceLevels() {
        return 0;
    }

    @Override
    public int totalNumberOfBidPriceLevels() {
        return 0;
    }

    @Override
    public int totalNumberOfAskPriceLevels() {
        return 0;
    }

    @Override
    public double getAskPriceAtPriceLevel(int priceLevel) {
        return 0;
    }

    @Override
    public double getBidPriceAtPriceLevel(int priceLevel) {
        return 0;
    }

    @Override
    public Optional<Order> getBestBidOrder() {
        return Optional.empty();
    }

    @Override
    public Optional<Order> getBestAskOrder() {
        return Optional.empty();
    }
}
