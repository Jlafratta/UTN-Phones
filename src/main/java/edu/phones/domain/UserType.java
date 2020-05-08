package main.java.edu.phones.domain;

public class UserType {

    Integer typeId;
    String name;

    public UserType(Integer typeId, String name) {
        this.typeId = typeId;
        this.name = name;
    }

    public UserType(String name) {
        this.name = name;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "typeId=" + typeId +
                ", name='" + name + '\'' +
                '}';
    }
}
