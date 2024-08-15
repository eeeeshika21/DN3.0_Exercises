package com.example.employee_management_system.repository.primary;

import com.example.employee_management_system.entity.primary.Employee;
import com.example.employee_management_system.projection.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Custom query methods using method name keywords
    List<Employee> findByDepartmentName(String departmentName);
    List<Employee> findByNameStartingWithAndEmailEndingWith(String namePrefix, String emailSuffix);

    // Custom query using @Query annotation with JPQL
    @Query("SELECT e FROM Employee e WHERE e.department.name = :deptName AND e.email LIKE %:emailDomain")
    List<Employee> findEmployeesByDepartmentAndEmailDomain(@Param("deptName") String departmentName, @Param("emailDomain") String emailDomain);

    // Custom query using @Query annotation with native SQL
    @Query(value = "SELECT * FROM employees e WHERE e.name LIKE %:keyword% OR e.email LIKE %:keyword%", nativeQuery = true)
    List<Employee> searchEmployees(@Param("keyword") String keyword);
    
    // Add a new method for paginated and sorted search
    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:keyword% OR e.email LIKE %:keyword%")
    Page<Employee> searchEmployees(@Param("keyword") String keyword, Pageable pageable);

    // Add a method for paginated and sorted list of all employees
    Page<Employee> findAll(Pageable pageable);

    List<EmployeeBasicInfo> findBy();  // This will return all employees as EmployeeBasicInfo

    @Query("SELECT e FROM Employee e WHERE e.department.name = :departmentName")
    List<EmployeeBasicInfo> findBasicInfoByDepartmentName(@Param("departmentName") String departmentName);

    @Query("SELECT e FROM Employee e")
    List<EmployeeWithDepartment> findAllEmployeesWithDepartment();

    @Query("SELECT new com.example.EmployeeManagementSystem.projection.EmployeeDetailDTO(e.id, e.name, e.email, e.department.name, e.createdBy, e.lastModifiedBy) FROM Employee e")
    List<EmployeeDetailDTO> findAllEmployeeDetails();
    Employee findByEmail(String email);
}