package com.example.demo.entites;


import jakarta.persistence.*;

import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private String department;

    private Date hireDate;

    @Column(nullable = true)
    private String imagePath ;


}
