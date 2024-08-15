package com.example.employee_management_system.projection;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeWithDepartment {
    Long getId();
    String getName();
    String getEmail();
    
    @Value("#{target.department.name}")
    String getDepartmentName();
}