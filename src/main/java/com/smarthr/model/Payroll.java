package com.smarthr.model;
import jakarta.persistence.*;
@Entity @Table(name="payroll")
public class Payroll {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private Employee employee;
 @Column(nullable=false) private String month;
 private Double basicSalary=0.0, allowances=0.0, deductions=0.0, netSalary=0.0;
 public Long getId(){return id;} public void setId(Long v){id=v;} public Employee getEmployee(){return employee;} public void setEmployee(Employee v){employee=v;}
 public String getMonth(){return month;} public void setMonth(String v){month=v;} public Double getBasicSalary(){return basicSalary;} public void setBasicSalary(Double v){basicSalary=v;}
 public Double getAllowances(){return allowances;} public void setAllowances(Double v){allowances=v;} public Double getDeductions(){return deductions;} public void setDeductions(Double v){deductions=v;}
 public Double getNetSalary(){return netSalary;} public void setNetSalary(Double v){netSalary=v;}
}
