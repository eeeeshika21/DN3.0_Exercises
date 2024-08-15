package com.example.employee_management_system.entity.secondary;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "secondary_employees")
public class SecondaryEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String position;
}
