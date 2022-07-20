package com.bookstore.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String address;

    @Column(length = 100, unique = true)
    @Email
    private String email;

    @Column
    @JsonIgnore
    private String password;

    @Column
    @Pattern(regexp = "(\\+\\d{2} ?)?(\\d{3} \\d{3} \\d{3}|\\d{9})")
    private String phone;

    @OneToMany(mappedBy = "clientEntity", cascade = CascadeType.ALL)
    private Set<OrderEntity> orders;

    public ClientEntity(String name, String surname, String address, String email, String password, String phone) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.orders = new HashSet<>();
    }

    public ClientEntity() {
    }

    public Long getId() {
        return id;
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

    public Set<OrderEntity> getOrders() {
        return orders;
    }
}
