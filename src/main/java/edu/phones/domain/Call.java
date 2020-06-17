package edu.phones.domain;

import java.sql.Date;

public class Call {

    Integer callId;
    Integer duration;
    Double cost;
    Double totalCost;
    Double price;
    Double totalPrice;
    Date date;
    PhoneLine origin;
    PhoneLine destination;
    Bill bill;
    Tariff tariff;

    public Call(Integer callId, Integer duration, Double cost, Double totalCost, Double price, Double totalPrice, Date date, PhoneLine origin, PhoneLine destination, Bill bill, Tariff tariff) {
        this.callId = callId;
        this.duration = duration;
        this.cost = cost;
        this.totalCost = totalCost;
        this.price = price;
        this.totalPrice = totalPrice;
        this.date = date;
        this.origin = origin;
        this.destination = destination;
        this.bill = bill;
        this.tariff = tariff;
    }

    public Call(Integer duration, Double cost, Double totalCost, Double price, Double totalPrice, Date date, PhoneLine origin, PhoneLine destination, Bill bill, Tariff tariff) {
        this.duration = duration;
        this.cost = cost;
        this.totalCost = totalCost;
        this.price = price;
        this.totalPrice = totalPrice;
        this.date = date;
        this.origin = origin;
        this.destination = destination;
        this.bill = bill;
        this.tariff = tariff;
    }

    public Integer getCallId() {
        return callId;
    }

    public void setCallId(Integer callId) {
        this.callId = callId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PhoneLine getOrigin() {
        return origin;
    }

    public void setOrigin(PhoneLine origin) {
        this.origin = origin;
    }

    public PhoneLine getDestination() {
        return destination;
    }

    public void setDestination(PhoneLine destination) {
        this.destination = destination;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    @Override
    public String toString() {
        return "Call{" +
                "callId=" + callId +
                ", duration=" + duration +
                ", cost=" + cost +
                ", totalCost=" + totalCost +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", date=" + date +
                ", origin=" + origin +
                ", destination=" + destination +
                ", bill=" + bill +
                ", tariff=" + tariff +
                '}';
    }
}
