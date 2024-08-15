package com.example.employee_management_system.repository.primary;

import com.example.employee_management_system.entity.primary.Department;
import com.example.employee_management_system.projection.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Custom query methods using method name keywords
    List<Department> findByNameContaining(String keyword);
    Department findFirstByOrderByNameAsc();

    // Custom query using @Query annotation
    @Query("SELECT d FROM Department d WHERE SIZE(d.employees) > :count")
    List<Department> findDepartmentsWithMoreEmployeesThan(@Param("count") int count);

    // Named query
    @Query(name = "Department.findByEmployeeEmail")
    Department findDepartmentByEmployeeEmail(@Param("email") String email);

    List<DepartmentBasicInfo> findBy();

    List<DepartmentWithEmployeeCount> findAllProjectedBy();
}
