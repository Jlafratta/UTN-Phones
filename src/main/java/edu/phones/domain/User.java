package edu.phones.domain;

public class User {

    Integer userId;
    String username;
    String password;
    UserProfile userProfile;
    City city;

    public User(Integer userId, String username, String password, UserProfile userProfile, City city) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userProfile = userProfile;
        this.city = city;
    }

    public User(String username, String password, UserProfile userProfile, City city) {
        this.username = username;
        this.password = password;
        this.userProfile = userProfile;
        this.city = city;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "\nUser [" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + "\']" +
                "\n" + userProfile +
                "\n" + city ;
    }
}
