package com.example.employee_management_system.entity.primary;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

import com.example.employee_management_system.entity.Auditable;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "departments")
@NamedQueries({
    @NamedQuery(name = "Department.findByEmployeeEmail",
                query = "SELECT DISTINCT e.department FROM Employee e WHERE e.email = :email")
})
public class Department extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();
}