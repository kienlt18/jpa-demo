package com.example.demojpa.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {
    private Long id;

    private String name;

    private int age;

    private String address;

    private String phone;

    private String email;

    public StudentDTO() {
    }

    public StudentDTO(Long id, String name, int age, String address, String phone, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
}
