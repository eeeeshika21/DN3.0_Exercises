package com.example.employee_management_system.controller;


import com.example.employee_management_system.entity.primary.Employee;
import com.example.employee_management_system.entity.secondary.SecondaryEmployee;
import com.example.employee_management_system.repository.primary.EmployeeRepository;
import com.example.employee_management_system.repository.secondary.SecondaryEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dual")
public class DualDataSourceController {

    @Autowired
    private EmployeeRepository primaryEmployeeRepository;

    @Autowired
    private SecondaryEmployeeRepository secondaryEmployeeRepository;

    @PostMapping("/primary")
    public Employee createPrimaryEmployee(@RequestBody Employee employee) {
        return primaryEmployeeRepository.save(employee);
    }

    @PostMapping("/secondary")
    public SecondaryEmployee createSecondaryEmployee(@RequestBody SecondaryEmployee employee) {
        return secondaryEmployeeRepository.save(employee);
    }

    @GetMapping("/primary")
    public List<Employee> getAllPrimaryEmployees() {
        return primaryEmployeeRepository.findAll();
    }

    @GetMapping("/secondary")
    public List<SecondaryEmployee> getAllSecondaryEmployees() {
        return secondaryEmployeeRepository.findAll();
    }
}