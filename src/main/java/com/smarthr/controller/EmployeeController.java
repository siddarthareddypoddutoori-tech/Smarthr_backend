package com.smarthr.controller;
import com.smarthr.model.*; import com.smarthr.repo.EmployeeRepository; import org.springframework.web.bind.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder; import java.util.*;
@RestController @RequestMapping("/api/employees")
public class EmployeeController {
 private final EmployeeRepository repo; private final PasswordEncoder enc; public EmployeeController(EmployeeRepository r,PasswordEncoder e){repo=r;enc=e;}
 @GetMapping public List<Employee> all(){return repo.findAll();}
 @GetMapping("/{id}") public Employee one(@PathVariable Long id){return repo.findById(id).orElseThrow();}
 @PostMapping public Employee create(@RequestBody Employee e){e.setId(null);e.setPassword(enc.encode(e.getPassword()==null?"ChangeMe123":e.getPassword()));return repo.save(e);}
 @PutMapping("/{id}") public Employee update(@PathVariable Long id,@RequestBody Employee x){Employee e=repo.findById(id).orElseThrow();e.setName(x.getName());e.setEmail(x.getEmail());e.setPhone(x.getPhone());e.setDepartment(x.getDepartment());e.setJobTitle(x.getJobTitle());e.setJoiningDate(x.getJoiningDate());e.setRole(x.getRole());e.setSalary(x.getSalary());if(x.getPassword()!=null&&!x.getPassword().isBlank())e.setPassword(enc.encode(x.getPassword()));return repo.save(e);}
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){repo.deleteById(id);}
}
