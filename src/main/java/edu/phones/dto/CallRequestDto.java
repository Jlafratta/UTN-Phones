package edu.phones.dto;

public class CallRequestDto {

    String name;
    String lastname;
    Integer duration;

    public CallRequestDto(String name, String lastname, Integer duration) {
        this.name = name;
        this.lastname = lastname;
        this.duration = duration;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
