package com.bookstore.model.responses;

import com.bookstore.model.entities.ClientEntity;

public class ClientRestModel {

    private String name;
    private String surname;
    private String address;
    private String email;
    private String password;
    private String phone;

    public ClientRestModel(String name, String surname, String address, String email, String password, String phone) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public ClientRestModel() {
    }

    public ClientRestModel(ClientEntity entity) {
        this.name = entity.getName();
        this.surname = entity.getSurname();
        this.address = entity.getAddress();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.phone = entity.getPhone();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }
}
