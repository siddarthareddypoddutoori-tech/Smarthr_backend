package com.smarthr.controller;
import com.smarthr.model.Employee; import com.smarthr.model.Role; import com.smarthr.repo.*; import org.springframework.http.HttpStatus; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException; import java.util.*;
@RestController @RequestMapping("/api/dashboard")
public class DashboardController {
 private final EmployeeRepository employees; private final AttendanceRepository attendance; private final LeaveRequestRepository leaves; private final PayrollRepository payroll; private final PerformanceRepository performance;
 public DashboardController(EmployeeRepository e,AttendanceRepository a,LeaveRequestRepository l,PayrollRepository p,PerformanceRepository f){employees=e;attendance=a;leaves=l;payroll=p;performance=f;}
 @GetMapping public Map<String,Object> stats(Authentication authentication){
  Employee current=employees.findByEmail(authentication.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED));
  if(current.getRole()==Role.EMPLOYEE)return Map.of("attendanceRecords",attendance.countByEmployeeId(current.getId()),"leaveRequests",leaves.countByEmployeeId(current.getId()));
  return Map.of("employees",employees.count(),"attendanceRecords",attendance.count(),"leaveRequests",leaves.count(),"payrollRecords",payroll.count(),"performanceReviews",performance.count());
 }
}
