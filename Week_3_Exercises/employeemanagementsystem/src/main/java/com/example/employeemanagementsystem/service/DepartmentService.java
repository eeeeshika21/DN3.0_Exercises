package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dto.DepartmentDTO;
import com.example.employeemanagementsystem.dto.EmployeeDTO;
import com.example.employeemanagementsystem.model.Department;
import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.repository.DepartmentRepository;
import com.example.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ResponseEntity<DepartmentDTO> getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(department -> ResponseEntity.ok(convertToDTO(department)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setName(departmentDTO.getName());
        // Set employees if necessary
        return convertToDTO(departmentRepository.save(department));
    }

    public ResponseEntity<DepartmentDTO> updateDepartment(Long id, DepartmentDTO departmentDTO) {
        return departmentRepository.findById(id)
                .map(department -> {
                    department.setName(departmentDTO.getName());
                    // Update employees if necessary
                    return ResponseEntity.ok(convertToDTO(departmentRepository.save(department)));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deleteDepartment(Long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setEmployees(department.getEmployees().stream()
                .map(this::convertToEmployeeDTO)
                .collect(Collectors.toSet()));
        return dto;
    }

    private EmployeeDTO convertToEmployeeDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setDepartmentId(employee.getDepartment().getId());
        return dto;
    }
}
