package com.smarthr.controller;
import com.smarthr.model.*; import com.smarthr.repo.*; import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException; import java.util.*;
@RestController @RequestMapping("/api/attendance")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public class AttendanceController {
 private final AttendanceRepository repo; private final EmployeeRepository employees; public AttendanceController(AttendanceRepository r,EmployeeRepository e){repo=r;employees=e;}
 @GetMapping public List<Attendance> all(){return repo.findAll();}
 @PostMapping public Attendance create(@RequestBody @Valid Map<String,Object> x){
  if (x == null || x.get("employeeId") == null || x.get("date") == null || x.get("present") == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "employeeId, date and present are required");
  Attendance a=new Attendance();
  a.setEmployee(employees.findById(Long.valueOf(x.get("employeeId").toString())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found")));
  a.setDate(java.time.LocalDate.parse(x.get("date").toString()));
  a.setPresent(Boolean.parseBoolean(x.get("present").toString()));
  a.setStatus((String)x.getOrDefault("status", a.isPresent()?"Present":"Absent"));
  return repo.save(a);
 }
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){if(!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendance record not found"); repo.deleteById(id);}
}
