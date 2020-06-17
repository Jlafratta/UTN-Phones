package edu.phones.domain;

public class Tariff {

    Integer key;
    Double cost;
    Double price;

    public Tariff(Integer key, Double cost, Double price) {
        this.key = key;
        this.cost = cost;
        this.price = price;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "key=" + key +
                ", cost=" + cost +
                ", price=" + price +
                '}';
    }
}
