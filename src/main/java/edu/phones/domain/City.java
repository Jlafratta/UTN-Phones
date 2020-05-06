package main.java.edu.phones.domain;

public class City {

    Integer cityId;
    String prefix;
    String name;
    Province province;

    public City(Integer cityId, String prefix, String name, Province province) {
        this.cityId = cityId;
        this.prefix = prefix;
        this.name = name;
        this.province = province;
    }

    public City(String prefix, String name, Province province) {
        this.prefix = prefix;
        this.name = name;
        this.province = province;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityId=" + cityId +
                ", prefix='" + prefix + '\'' +
                ", name='" + name + '\'' +
                ", province=" + province +
                '}';
    }
}
