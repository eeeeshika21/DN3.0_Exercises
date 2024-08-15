package com.example.employee_management_system.repository.secondary;

import com.example.employee_management_system.entity.secondary.SecondaryEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecondaryEmployeeRepository extends JpaRepository<SecondaryEmployee, Long> {
}
