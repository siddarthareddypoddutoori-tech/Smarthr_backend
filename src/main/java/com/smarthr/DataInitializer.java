package com.smarthr;
import com.smarthr.model.*; import com.smarthr.repo.EmployeeRepository; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration; import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration public class DataInitializer {
 @Bean CommandLineRunner seed(EmployeeRepository repo,PasswordEncoder enc){return args->{if(repo.findByEmail("admin@smarthr.com").isEmpty()){Employee e=new Employee();e.setName("HR Administrator");e.setEmail("admin@smarthr.com");e.setPassword(enc.encode("Admin@123"));e.setRole(Role.ADMIN);e.setDepartment("Human Resources");e.setJobTitle("HR Administrator");repo.save(e);}};}
}
