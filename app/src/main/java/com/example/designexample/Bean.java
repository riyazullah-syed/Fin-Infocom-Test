package com.example.designexample;

public class Bean {
    private String email;
    private String number;

    public Bean(String email, String number) {
        this.email = email;
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
