package com.smarthr.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="attendance", uniqueConstraints=@UniqueConstraint(columnNames={"employee_id","date"}))
public class Attendance {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private Employee employee;
 @Column(nullable=false) private LocalDate date;
 private boolean present; private String status;
 public Long getId(){return id;} public void setId(Long v){id=v;} public Employee getEmployee(){return employee;} public void setEmployee(Employee v){employee=v;}
 public LocalDate getDate(){return date;} public void setDate(LocalDate v){date=v;} public boolean isPresent(){return present;} public void setPresent(boolean v){present=v;}
 public String getStatus(){return status;} public void setStatus(String v){status=v;}
}
