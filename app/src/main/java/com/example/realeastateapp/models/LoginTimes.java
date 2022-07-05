package com.example.realeastateapp.models;

public class LoginTimes {

    private String username;
    private String login;
    private String logout;

    public LoginTimes(String username, String login, String logout) {
        this.username = username;
        this.login = login;
        this.logout = logout;
    }

    public String getUsername() {
        return username;
    }

    public String getLogin() {
        return login;
    }

    public String getLogout() {
        return logout;
    }
}
