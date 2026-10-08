package com.smarthr.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="leave_requests")
public class LeaveRequest {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private Employee employee;
 private LocalDate fromDate,toDate; private String reason;
 @Enumerated(EnumType.STRING) private LeaveStatus status=LeaveStatus.PENDING;
 private String reviewerComment;
 public Long getId(){return id;} public void setId(Long v){id=v;} public Employee getEmployee(){return employee;} public void setEmployee(Employee v){employee=v;}
 public LocalDate getFromDate(){return fromDate;} public void setFromDate(LocalDate v){fromDate=v;} public LocalDate getToDate(){return toDate;} public void setToDate(LocalDate v){toDate=v;}
 public String getReason(){return reason;} public void setReason(String v){reason=v;} public LeaveStatus getStatus(){return status;} public void setStatus(LeaveStatus v){status=v;}
 public String getReviewerComment(){return reviewerComment;} public void setReviewerComment(String v){reviewerComment=v;}
}
