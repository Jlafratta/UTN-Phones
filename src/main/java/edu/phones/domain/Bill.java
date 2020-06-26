package edu.phones.domain;


import java.util.Date;

public class Bill {

    Integer billId;
    Double cost;
    Double price;
    Date date;
    Date expireDate;
    Integer countCalls;
    PhoneLine pLine;

    public Bill(Integer billId, Double cost, Double price, Date date, Date expireDate, Integer countCalls, PhoneLine pLine) {
        this.billId = billId;
        this.cost = cost;
        this.price = price;
        this.date = date;
        this.expireDate = expireDate;
        this.countCalls = countCalls;
        this.pLine = pLine;
    }

    public Bill(Double cost, Double price, Date date, Date expireDate, Integer countCalls, PhoneLine pLine) {
        this.cost = cost;
        this.price = price;
        this.date = date;
        this.expireDate = expireDate;
        this.countCalls = countCalls;
        this.pLine = pLine;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getCountCalls() {
        return countCalls;
    }

    public void setCountCalls(Integer countCalls) {
        this.countCalls = countCalls;
    }

    public PhoneLine getpLine() {
        return pLine;
    }

    public void setpLine(PhoneLine pLine) {
        this.pLine = pLine;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "billId=" + billId +
                ", cost=" + cost +
                ", total=" + price +
                ", date=" + date +
                ", expireDate=" + expireDate +
                ", countCalls=" + countCalls +
                ", pLine=" + pLine +
                '}';
    }
}
