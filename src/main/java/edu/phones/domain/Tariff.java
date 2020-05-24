package edu.phones.domain;

public class Tariff {

    Integer key;
    Double value;

    public Tariff(Integer key, Double value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
