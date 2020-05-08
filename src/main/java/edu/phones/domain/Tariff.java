package edu.phones.domain;

public class Tariff {

    Integer key;
    Integer value;

    public Tariff(Integer key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
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
