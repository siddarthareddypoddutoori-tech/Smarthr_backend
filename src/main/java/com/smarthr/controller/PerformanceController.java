package com.smarthr.controller;
import com.smarthr.model.*; import com.smarthr.repo.*; import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException; import java.util.*;
@RestController @RequestMapping("/api/performance")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public class PerformanceController {
 private final PerformanceRepository repo; private final EmployeeRepository employees; public PerformanceController(PerformanceRepository r,EmployeeRepository e){repo=r;employees=e;}
 @GetMapping public List<Performance> all(){return repo.findAll();}
 @PostMapping public Performance create(@RequestBody @Valid Map<String,Object> x){
  if (x == null || x.get("employeeId") == null || x.get("reviewDate") == null || x.get("rating") == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "employeeId, reviewDate and rating are required");
  Performance p=new Performance();
  p.setEmployee(employees.findById(Long.valueOf(x.get("employeeId").toString())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found")));
  p.setReviewDate(java.time.LocalDate.parse(x.get("reviewDate").toString()));
  p.setRating(Double.valueOf(x.get("rating").toString()));
  p.setGoals((String)x.get("goals"));
  p.setFeedback((String)x.get("feedback"));
  return repo.save(p);
 }
}
