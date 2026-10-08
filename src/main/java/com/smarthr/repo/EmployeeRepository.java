package com.smarthr.repo; import com.smarthr.model.Employee; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface EmployeeRepository extends JpaRepository<Employee,Long>{ Optional<Employee> findByEmail(String email); long countByDepartment(String department); }
