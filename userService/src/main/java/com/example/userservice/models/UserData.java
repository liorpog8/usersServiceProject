package com.example.userservice.models;

import org.springframework.data.annotation.Id;


public class UserData implements Comparable<UserData>{




    @Id
    private String id;
    private String name;
    private String lastName;
    private int birthYear;


    public UserData() {}

    public UserData(String name, String lastName, int birthYear) {
        this.name = name;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    @Override
    public int compareTo(UserData d) {
        return this.birthYear - d.getBirthYear();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%s, name='%s', lastName='%s', birthYear='%s']",
                id, name, lastName, birthYear);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj == null || obj.getClass() != this.getClass()) { return false; }
        UserData user = (UserData) obj;
        return this.name.equals(user.getName()) &&
                this.lastName.equals(user.getLastName()) &&
                this.birthYear == user.getBirthYear();
    }


}
