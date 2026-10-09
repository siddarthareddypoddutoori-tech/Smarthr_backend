package com.smarthr.controller;
import com.smarthr.model.*; import com.smarthr.repo.EmployeeRepository; import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.server.ResponseStatusException; import java.util.*;
@RestController @RequestMapping("/api/employees")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public class EmployeeController {
 private final EmployeeRepository repo; private final PasswordEncoder enc; public EmployeeController(EmployeeRepository r,PasswordEncoder e){repo=r;enc=e;}
 @GetMapping public List<Employee> all(){return repo.findAll();}
 @GetMapping("/{id}") public Employee one(@PathVariable Long id){return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));}
 @PostMapping public Employee create(@RequestBody @Valid Employee e){
  if (e.getEmail()==null || e.getEmail().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
  if (repo.findByEmail(e.getEmail().trim()).isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
  e.setId(null); e.setEmail(e.getEmail().trim()); e.setPassword(enc.encode(e.getPassword()==null || e.getPassword().isBlank()?"ChangeMe123":e.getPassword()));
  return repo.save(e);
 }
 @PutMapping("/{id}") public Employee update(@PathVariable Long id,@RequestBody @Valid Employee x){
  Employee e=repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
  String email=x.getEmail()==null?null:x.getEmail().trim();
  if (email==null || email.isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
  repo.findByEmail(email).filter(existing -> !existing.getId().equals(id)).ifPresent(existing -> { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists"); });
  e.setName(x.getName()); e.setEmail(email); e.setPhone(x.getPhone()); e.setDepartment(x.getDepartment()); e.setJobTitle(x.getJobTitle()); e.setJoiningDate(x.getJoiningDate()); e.setRole(x.getRole()==null?Role.EMPLOYEE:x.getRole()); e.setSalary(x.getSalary());
  if(x.getPassword()!=null&&!x.getPassword().isBlank())e.setPassword(enc.encode(x.getPassword()));
  return repo.save(e);
 }
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){if(!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"); repo.deleteById(id);}
}
