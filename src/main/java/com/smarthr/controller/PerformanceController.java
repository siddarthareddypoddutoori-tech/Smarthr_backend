package com.smarthr.controller;
import com.smarthr.model.*; import com.smarthr.repo.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/performance")
public class PerformanceController {
 private final PerformanceRepository repo; private final EmployeeRepository employees; public PerformanceController(PerformanceRepository r,EmployeeRepository e){repo=r;employees=e;}
 @GetMapping public List<Performance> all(){return repo.findAll();}
 @PostMapping public Performance create(@RequestBody Map<String,Object> x){Performance p=new Performance();p.setEmployee(employees.findById(Long.valueOf(x.get("employeeId").toString())).orElseThrow());p.setReviewDate(java.time.LocalDate.parse(x.get("reviewDate").toString()));p.setRating(Double.valueOf(x.get("rating").toString()));p.setGoals((String)x.get("goals"));p.setFeedback((String)x.get("feedback"));return repo.save(p);}
}
