package main.java.edu.phones.domain;

public class Province {

    Integer provinceId;
    String name;

    public Province(Integer provinceId, String name) {
        this.provinceId = provinceId;
        this.name = name;
    }

    public Province(String name) {
        this.name = name;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Province{" +
                "provinceId=" + provinceId +
                ", name='" + name + '\'' +
                '}';
    }
}
