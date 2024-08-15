package com.example.employee_management_system.projection;

import lombok.Value;

@Value
public class EmployeeDetailDTO {
    Long id;
    String name;
    String email;
    String departmentName;
    String createdBy;
    String lastModifiedBy;
}