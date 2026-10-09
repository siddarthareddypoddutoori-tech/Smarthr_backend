package com.smarthr.controller;
import com.smarthr.model.*; import com.smarthr.repo.*; import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException; import java.util.*;
@RestController @RequestMapping("/api/payroll")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public class PayrollController {
 private final PayrollRepository repo; private final EmployeeRepository employees; public PayrollController(PayrollRepository r,EmployeeRepository e){repo=r;employees=e;}
 @GetMapping public List<Payroll> all(){return repo.findAll();}
 @PostMapping public Payroll create(@RequestBody @Valid Map<String,Object> x){
  if (x == null || x.get("employeeId") == null || x.get("month") == null || x.get("basicSalary") == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "employeeId, month and basicSalary are required");
  Payroll p=new Payroll();
  p.setEmployee(employees.findById(Long.valueOf(x.get("employeeId").toString())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found")));
  p.setMonth((String)x.get("month"));
  p.setBasicSalary(Double.valueOf(x.get("basicSalary").toString()));
  p.setAllowances(Double.valueOf(x.getOrDefault("allowances",0).toString()));
  p.setDeductions(Double.valueOf(x.getOrDefault("deductions",0).toString()));
  p.setNetSalary(p.getBasicSalary()+p.getAllowances()-p.getDeductions());
  return repo.save(p);
 }
}
