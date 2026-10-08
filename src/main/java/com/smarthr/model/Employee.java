package com.smarthr.model;
import jakarta.persistence.*; import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
@Entity @Table(name="employees")
public class Employee {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(nullable=false,unique=true) private String email;
 @Column(nullable=false) private String password;
 private String phone, department, jobTitle;
 private LocalDate joiningDate;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role=Role.EMPLOYEE;
 private Double salary=0.0;
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public String getName(){return name;} public void setName(String v){name=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 @JsonIgnore public String getPassword(){return password;} public void setPassword(String v){password=v;}
 public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
 public String getDepartment(){return department;} public void setDepartment(String v){department=v;}
 public String getJobTitle(){return jobTitle;} public void setJobTitle(String v){jobTitle=v;}
 public LocalDate getJoiningDate(){return joiningDate;} public void setJoiningDate(LocalDate v){joiningDate=v;}
 public Role getRole(){return role;} public void setRole(Role v){role=v;}
 public Double getSalary(){return salary;} public void setSalary(Double v){salary=v;}
}
