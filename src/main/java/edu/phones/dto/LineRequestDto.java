package edu.phones.dto;

public class LineRequestDto {

    String number;
    String cityName;
    Integer cantCalls;

    public LineRequestDto(String number, String cityName, Integer cantCalls) {
        this.number = number;
        this.cityName = cityName;
        this.cantCalls = cantCalls;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCantCalls() {
        return cantCalls;
    }

    public void setCantCalls(Integer cantCalls) {
        this.cantCalls = cantCalls;
    }
}
