package com.example.employee_management_system.controller;

import com.example.employee_management_system.entity.primary.Employee;
import com.example.employee_management_system.projection.EmployeeBasicInfo;
import com.example.employee_management_system.projection.EmployeeDetailDTO;
import com.example.employee_management_system.projection.EmployeeWithDepartment;
import com.example.employee_management_system.repository.primary.EmployeeRepository;
import com.example.employee_management_system.service.EmployeeBatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

     @Autowired
    private EmployeeBatchService employeeBatchService;

    // Create
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // Read all
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return ResponseEntity.ok(employee);
    }
    
    @GetMapping("/search")
    public List<Employee> searchEmployees(@RequestParam String keyword) {
        return employeeRepository.searchEmployees(keyword);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        
        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDepartment(employeeDetails.getDepartment());
        
        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        
        employeeRepository.delete(employee);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/search")
    public ResponseEntity<Page<Employee>> searchEmployees(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sortObj = Sort.by(direction, sort[0]);
        Pageable pageable = PageRequest.of(page, size, sortObj);

        Page<Employee> employeePage;
        if (keyword != null && !keyword.isEmpty()) {
            employeePage = employeeRepository.searchEmployees(keyword, pageable);
        } else {
            employeePage = employeeRepository.findAll(pageable);
        }

        return ResponseEntity.ok(employeePage);
    }

    @GetMapping("/basic")
    public List<EmployeeBasicInfo> getAllEmployeesBasicInfo() {
        return employeeRepository.findBy();
    }

    @GetMapping("/basic/by-department")
    public List<EmployeeBasicInfo> getEmployeesBasicInfoByDepartment(@RequestParam String departmentName) {
        return employeeRepository.findBasicInfoByDepartmentName(departmentName);
    }

    @GetMapping("/with-department")
    public List<EmployeeWithDepartment> getEmployeesWithDepartment() {
        return employeeRepository.findAllEmployeesWithDepartment();
    }

    @GetMapping("/details")
    public List<EmployeeDetailDTO> getEmployeeDetails() {
        return employeeRepository.findAllEmployeeDetails();
    }

    @PostMapping("/batch")
    public ResponseEntity<String> batchInsertEmployees(@RequestBody List<Employee> employees) {
        employeeBatchService.batchInsertEmployees(employees);
        return ResponseEntity.ok("Batch insert completed");
    }

    @PutMapping("/batch")
    public ResponseEntity<String> batchUpdateEmployees(@RequestBody List<Employee> employees) {
        employeeBatchService.batchUpdateEmployees(employees);
        return ResponseEntity.ok("Batch update completed");
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
