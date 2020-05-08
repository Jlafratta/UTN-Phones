package edu.phones.domain;

import java.sql.Date;

public class Bill {

    Integer billId;
    Integer cost;
    Integer total;
    Date date;
    Date expireDate;
    Integer countCalls;
    PhoneLine pLine;

    public Bill(Integer billId, Integer cost, Integer total, Date date, Date expireDate, Integer countCalls, PhoneLine pLine) {
        this.billId = billId;
        this.cost = cost;
        this.total = total;
        this.date = date;
        this.expireDate = expireDate;
        this.countCalls = countCalls;
        this.pLine = pLine;
    }

    public Bill(Integer cost, Integer total, Date date, Date expireDate, Integer countCalls, PhoneLine pLine) {
        this.cost = cost;
        this.total = total;
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

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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
                ", total=" + total +
                ", date=" + date +
                ", expireDate=" + expireDate +
                ", countCalls=" + countCalls +
                ", pLine=" + pLine +
                '}';
    }
}
