package edu.phones.domain;

public class UserProfile {

    Integer profileId;
    String name;
    String lastname;
    Integer dni;

    public UserProfile(Integer profileId, String name, String lastname, Integer dni) {
        this.profileId = profileId;
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
    }

    public UserProfile(String name, String lastname, Integer dni) {
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "UserProfile [" +
                "profileId=" + profileId +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dni=" + dni +
                ']';
    }

}
