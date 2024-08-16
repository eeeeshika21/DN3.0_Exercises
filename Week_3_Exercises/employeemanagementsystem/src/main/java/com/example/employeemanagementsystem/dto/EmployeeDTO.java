package com.example.employeemanagementsystem.dto;

import lombok.Data;

@Data
public class EmployeeDTO {

    private Long id;
    private String name;
    private String email;
    private Long departmentId; // Include only necessary fields
}
