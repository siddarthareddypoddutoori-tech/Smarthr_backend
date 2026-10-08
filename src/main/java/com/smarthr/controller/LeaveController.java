package com.smarthr.controller;
import com.smarthr.model.*; import com.smarthr.repo.*; import jakarta.validation.Valid; import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.NotNull; import org.springframework.http.HttpStatus; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException; import java.util.*;
@RestController @RequestMapping("/api/leaves")
public class LeaveController {
 private final LeaveRequestRepository repo; private final EmployeeRepository employees; public LeaveController(LeaveRequestRepository r,EmployeeRepository e){repo=r;employees=e;}
 public record LeaveInput(@NotNull java.time.LocalDate fromDate,@NotNull java.time.LocalDate toDate,@NotBlank String reason) {}
 public record ReviewInput(@NotBlank String status,String reviewerComment) {}
 @GetMapping public List<LeaveRequest> all(Authentication authentication){
  if(authentication.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN")||a.getAuthority().equals("ROLE_HR")))return repo.findAll();
  Employee employee=employees.findByEmail(authentication.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED));
  return repo.findAllByEmployeeIdOrderByFromDateDesc(employee.getId());
 }
 @PostMapping public LeaveRequest create(@RequestBody @Valid LeaveInput input,Authentication authentication){
  if(input.toDate().isBefore(input.fromDate()))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"End date must be on or after start date");
  Employee employee=employees.findByEmail(authentication.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED));
  LeaveRequest request=new LeaveRequest();request.setEmployee(employee);request.setFromDate(input.fromDate());request.setToDate(input.toDate());request.setReason(input.reason().trim());request.setStatus(LeaveStatus.PENDING);return repo.save(request);
 }
 @PutMapping("/{id}/status") @PreAuthorize("hasAnyRole('ADMIN','HR')")
 public LeaveRequest status(@PathVariable Long id,@RequestBody @Valid ReviewInput input){
  LeaveStatus nextStatus;
  try{nextStatus=LeaveStatus.valueOf(input.status().toUpperCase());}
  catch(IllegalArgumentException ex){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Status must be APPROVED or REJECTED");}
  if(nextStatus==LeaveStatus.PENDING)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"A leave request can only be approved or rejected");
  LeaveRequest request=repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Leave request not found"));
  request.setStatus(nextStatus);request.setReviewerComment(input.reviewerComment());return repo.save(request);
 }
}
