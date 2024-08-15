package com.example.employee_management_system.service;

import com.example.employee_management_system.entity.primary.Employee;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Service
public class EmployeeBatchService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void batchInsertEmployees(List<Employee> employees) {
        Session session = entityManager.unwrap(Session.class);
        for (int i = 0; i < employees.size(); i++) {
            session.persist(employees.get(i));
            if (i % 50 == 0 && i > 0) {
                session.flush();
                session.clear();
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Transactional
    public void batchUpdateEmployees(List<Employee> employees) {
        Session session = entityManager.unwrap(Session.class);
        for (int i = 0; i < employees.size(); i++) {
            session.update(employees.get(i));
            if (i % 50 == 0 && i > 0) {
                session.flush();
                session.clear();
            }
        }
    }
}
