package com.example.demojpa.entity;

import com.example.demojpa.controller.dto.StudentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.NamedQuery;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.ConstructorResult;
import javax.persistence.ColumnResult;

@Getter
@Setter
@Entity
@Table(name = "student")
@NamedQuery(name = "Student.findByAddress",
        query = "Select s from Student s where s.address = ?1")
@NamedNativeQuery(name = "Student.findByPhone",
        query = "Select s.id, s.name, s.age, s.address, s.phone, s.email from student s where phone = :phone",
        resultSetMapping = "Mapping.StudentDTO")
@SqlResultSetMapping(name = "Mapping.StudentDTO",
        classes = @ConstructorResult(targetClass = StudentDTO.class,
                columns = {@ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "age", type = Integer.class),
                        @ColumnResult(name = "address", type = String.class),
                        @ColumnResult(name = "phone", type = String.class),
                        @ColumnResult(name = "email", type = String.class),
                }))
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    public Student() {
    }

    public Student(Long id, String name, int age, String address, String phone, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
