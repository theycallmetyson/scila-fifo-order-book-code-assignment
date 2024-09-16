package com.scila.codetest.fifoorderbook;

import java.util.*;

import com.scila.codetest.fifoorderbook.api.Order;
import com.scila.codetest.fifoorderbook.api.Orderbook;
import com.scila.codetest.fifoorderbook.enums.OrderSide;

public class FifoOrderbook implements Orderbook {

    private final String orderbookId;

    private final PriorityQueue<Order> bidOrders;
    private final PriorityQueue<Order> askOrders;

    private final TreeMap<Double, List<Order>> bidOrderMap;
    private final TreeMap<Double, List<Order>> askOrderMap;

    private final Map<String, Order> orderMap;

    public FifoOrderbook(String orderbookId) {
        this.orderbookId = orderbookId;
        this.orderMap = new HashMap<>();

        this.bidOrders = new PriorityQueue<>(Comparator.comparingDouble(Order::price).reversed());
        this.bidOrderMap = new TreeMap<>(Collections.reverseOrder());

        this.askOrders = new PriorityQueue<>(Comparator.comparingDouble(Order::price));
        this.askOrderMap = new TreeMap<>();

    }

    @Override
    public void updateOrderbook(Order order) {
        switch (order.orderOperation()) {
            case ADD:
                addOrder(order);
                break;
            case UPDATE:
                updateOrder(order);
                break;
            case CANCEL:
                cancelOrder(order.orderId());
                break;
            default:
                throw new IllegalArgumentException("Unknown operation: " + order.orderOperation());
        }
    }

    private void addOrder(Order order) {
        orderMap.put(order.orderId(), order);
        if (order.orderSide() == OrderSide.BID) {
            bidOrders.add(order);
            addOrderToMap(order, bidOrderMap);
        } else {
            askOrders.add(order);
            addOrderToMap(order, askOrderMap);
        }
    }

    private void updateOrder(Order order) {
        cancelOrder(order.orderId());
        addOrder(order);
    }

    private void cancelOrder(String orderId) {
        Order order = orderMap.remove(orderId);
        if (order != null) {
            if (order.orderSide() == OrderSide.BID) {
                bidOrders.remove(order);
                removeOrderFromMap(order, bidOrderMap);
            } else {
                askOrders.remove(order);
                removeOrderFromMap(order, askOrderMap);
            }
        }
    }

    private void addOrderToMap(Order order, TreeMap<Double, List<Order>> map) {
        map.computeIfAbsent(order.price(), k -> new ArrayList<>()).add(order);
    }

    private void removeOrderFromMap(Order order, TreeMap<Double, List<Order>> map) {
        List<Order> ordersAtLevel = map.get(order.price());
        if (ordersAtLevel != null) {
            ordersAtLevel.remove(order);
            if (ordersAtLevel.isEmpty()) {
                map.remove(order.price());
            }
        }
    }

    @Override
    public String getOrderbookId() {
        return orderbookId;
    }

    @Override
    public Order getOrder(String orderId) {
        return orderMap.get(orderId);
    }

    @Override
    public double totalOrderVolume() {
        return totalBidVolume() + totalAskVolume();
    }

    @Override
    public double totalBidVolume() {
        return bidOrders.stream().mapToDouble(Order::currentVolume).sum();
    }

    @Override
    public double totalAskVolume() {
        return askOrders.stream().mapToDouble(Order::currentVolume).sum();
    }

    @Override
    public double getBestBidPrice() {
        return bidOrders.isEmpty() ? 0 : bidOrders.peek().price();
    }

    @Override
    public double getBestAskPrice() {
        return askOrders.isEmpty() ? 0 : askOrders.peek().price();
    }

    @Override
    public long totalNumberOfBidOrders() {
        return bidOrders.size();
    }

    @Override
    public long totalNumberOfAskOrders() {
        return askOrders.size();
    }

    @Override
    public long totalNumberOfActiveOrders() {
        return orderMap.size();
    }

    @Override
    public double totalVolumeAtPriceLevel(int priceLevel) {
        return totalBidVolumeAtPriceLevel(priceLevel) + totalAskVolumeAtPriceLevel(priceLevel);
    }

    @Override
    public double totalBidVolumeAtPriceLevel(int priceLevel) {
        List<Double> prices = new ArrayList<>(bidOrderMap.keySet());
        if (priceLevel < 1 || priceLevel > prices.size()) return 0;
        double price = prices.get(priceLevel - 1);
        List<Order> ordersAtLevel = bidOrderMap.get(price);
        if (ordersAtLevel == null) return 0;
        return ordersAtLevel.stream().mapToDouble(Order::currentVolume).sum();
    }

    @Override
    public double totalAskVolumeAtPriceLevel(int priceLevel) {
        List<Double> prices = new ArrayList<>(askOrderMap.keySet());
        if (priceLevel < 1 || priceLevel > prices.size()) return 0;
        double price = prices.get(priceLevel - 1);
        List<Order> ordersAtLevel = askOrderMap.get(price);
        if (ordersAtLevel == null) return 0;
        return ordersAtLevel.stream().mapToDouble(Order::currentVolume).sum();
    }

    @Override
    public int totalNumberOfPriceLevels() {
        return bidOrderMap.size() + askOrderMap.size();
    }

    @Override
    public int totalNumberOfBidPriceLevels() {
        return bidOrderMap.size();
    }

    @Override
    public int totalNumberOfAskPriceLevels() {
        return askOrderMap.size();
    }

    @Override
    public double getAskPriceAtPriceLevel(int priceLevel) {
        List<Double> prices = new ArrayList<>(askOrderMap.keySet());
        if (priceLevel < 1 || priceLevel > prices.size()) return 0;
        return prices.get(priceLevel - 1);
    }

    @Override
    public double getBidPriceAtPriceLevel(int priceLevel) {
        List<Double> prices = new ArrayList<>(bidOrderMap.keySet());
        if (priceLevel < 1 || priceLevel > prices.size()) return 0;
        return prices.get(priceLevel - 1);
    }

    @Override
    public Optional<Order> getBestBidOrder() {
        return bidOrders.isEmpty() ? Optional.empty() : Optional.of(bidOrders.peek());
    }

    @Override
    public Optional<Order> getBestAskOrder() {
        return askOrders.isEmpty() ? Optional.empty() : Optional.of(askOrders.peek());
    }
}
