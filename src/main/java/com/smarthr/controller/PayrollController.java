package com.smarthr.controller;
import com.smarthr.model.*; import com.smarthr.repo.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/payroll")
public class PayrollController {
 private final PayrollRepository repo; private final EmployeeRepository employees; public PayrollController(PayrollRepository r,EmployeeRepository e){repo=r;employees=e;}
 @GetMapping public List<Payroll> all(){return repo.findAll();}
 @PostMapping public Payroll create(@RequestBody Map<String,Object> x){Payroll p=new Payroll();p.setEmployee(employees.findById(Long.valueOf(x.get("employeeId").toString())).orElseThrow());p.setMonth((String)x.get("month"));p.setBasicSalary(Double.valueOf(x.get("basicSalary").toString()));p.setAllowances(Double.valueOf(x.getOrDefault("allowances",0).toString()));p.setDeductions(Double.valueOf(x.getOrDefault("deductions",0).toString()));p.setNetSalary(p.getBasicSalary()+p.getAllowances()-p.getDeductions());return repo.save(p);}
}
