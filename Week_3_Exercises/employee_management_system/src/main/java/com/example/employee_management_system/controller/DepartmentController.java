package com.example.employee_management_system.controller;

import com.example.employee_management_system.entity.primary.Department;
import com.example.employee_management_system.projection.DepartmentBasicInfo;
import com.example.employee_management_system.projection.DepartmentWithEmployeeCount;
import com.example.employee_management_system.repository.primary.DepartmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Create
    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        return departmentRepository.save(department);
    }

    // Read all
    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        return ResponseEntity.ok(department);
    }

    @GetMapping("/with-employees")
    public List<Department> getDepartmentsWithMoreEmployeesThan(@RequestParam int count) {
        return departmentRepository.findDepartmentsWithMoreEmployeesThan(count);
    }
    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        
        department.setName(departmentDetails.getName());
        
        Department updatedDepartment = departmentRepository.save(department);
        return ResponseEntity.ok(updatedDepartment);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        
        departmentRepository.delete(department);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/basic")
    public List<DepartmentBasicInfo> getDepartmentsBasicInfo() {
        return departmentRepository.findBy();
    }

    @GetMapping("/with-employee-count")
    public List<DepartmentWithEmployeeCount> getDepartmentsWithEmployeeCount() {
        return departmentRepository.findAllProjectedBy();
    }
}
