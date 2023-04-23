package com.example.penman.holderClasses;

public class User {

    public String first_name, surname, email;

    public User() {


    }

    public User(String first_name, String surname, String email) {
        this.first_name = first_name;
        this.surname = surname;
        this.email = email;

    }

    public String getFirst_name() {
        return first_name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }
}
