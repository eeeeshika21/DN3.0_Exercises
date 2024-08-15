package com.example.employee_management_system.projection;

import org.springframework.beans.factory.annotation.Value;

public interface DepartmentWithEmployeeCount {
    Long getId();
    String getName();
    
    @Value("#{target.employees.size()}")
    int getEmployeeCount();
}
