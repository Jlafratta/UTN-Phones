package edu.phones.dto;

import java.util.Date;

public class CallRequestDto {

    String numberOrigin;
    String citiyOrigin;
    String numberDestination;
    String cityDestination;
    Double totalPrice;
    Integer duration;
    Date call_date;

    public CallRequestDto(String numberOrigin, String citiyOrigin, String numberDestination, String cityDestination, Double totalPrice, Integer duration, Date call_date) {
        this.numberOrigin = numberOrigin;
        this.citiyOrigin = citiyOrigin;
        this.numberDestination = numberDestination;
        this.cityDestination = cityDestination;
        this.totalPrice = totalPrice;
        this.duration = duration;
        this.call_date = call_date;
    }

    public String getNumberOrigin() {
        return numberOrigin;
    }

    public void setNumberOrigin(String numberOrigin) {
        this.numberOrigin = numberOrigin;
    }

    public String getCitiyOrigin() {
        return citiyOrigin;
    }

    public void setCitiyOrigin(String citiyOrigin) {
        this.citiyOrigin = citiyOrigin;
    }

    public String getNumberDestination() {
        return numberDestination;
    }

    public void setNumberDestination(String numberDestination) {
        this.numberDestination = numberDestination;
    }

    public String getCityDestination() {
        return cityDestination;
    }

    public void setCityDestination(String cityDestination) {
        this.cityDestination = cityDestination;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getCall_date() {
        return call_date;
    }

    public void setCall_date(Date call_date) {
        this.call_date = call_date;
    }
}
