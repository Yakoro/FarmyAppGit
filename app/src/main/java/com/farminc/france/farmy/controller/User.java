package com.farminc.france.farmy.controller;

public class User {
    private String _name;

    public User(String name) {
        this._name = name;
    }

    public String getFirstName() {
        return _name;
    }

    public void setFirstName(String name) {
        _name = name;
    }
}
