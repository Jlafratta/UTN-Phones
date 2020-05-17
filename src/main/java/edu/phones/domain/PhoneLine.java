package edu.phones.domain;

public class PhoneLine {

    Integer pLineId;
    String number;
    Boolean state;
    User user;
    UserType userType;

    public PhoneLine(Integer pLineId, String number, Boolean state, User user, UserType userType) {
        this.pLineId = pLineId;
        this.number = number;
        this.state = state;
        this.user = user;
        this.userType = userType;
    }

    public PhoneLine(String number, Boolean state, User user, UserType userType) {
        this.number = number;
        this.state = state;
        this.user = user;
        this.userType = userType;
    }

    public Integer getpLineId() {
        return pLineId;
    }

    public void setpLineId(Integer pLineId) {
        this.pLineId = pLineId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "PhoneLine{" +
                "pLineId=" + pLineId +
                ", number='" + number + '\'' +
                ", user=" + user +
                ", userType=" + userType +
                '}';
    }
}
