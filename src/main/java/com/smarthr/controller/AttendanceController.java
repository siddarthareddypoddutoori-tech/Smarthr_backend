package com.smarthr.controller;
import com.smarthr.model.*; import com.smarthr.repo.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/attendance")
public class AttendanceController {
 private final AttendanceRepository repo; private final EmployeeRepository employees; public AttendanceController(AttendanceRepository r,EmployeeRepository e){repo=r;employees=e;}
 @GetMapping public List<Attendance> all(){return repo.findAll();}
 @PostMapping public Attendance create(@RequestBody Map<String,Object> x){Attendance a=new Attendance();a.setEmployee(employees.findById(Long.valueOf(x.get("employeeId").toString())).orElseThrow());a.setDate(java.time.LocalDate.parse(x.get("date").toString()));a.setPresent(Boolean.parseBoolean(x.get("present").toString()));a.setStatus((String)x.getOrDefault("status",a.isPresent()?"Present":"Absent"));return repo.save(a);}
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){repo.deleteById(id);}
}
