package com.smarthr.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="performance_reviews")
public class Performance {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private Employee employee;
 private LocalDate reviewDate; private Double rating; private String goals,feedback;
 public Long getId(){return id;} public void setId(Long v){id=v;} public Employee getEmployee(){return employee;} public void setEmployee(Employee v){employee=v;}
 public LocalDate getReviewDate(){return reviewDate;} public void setReviewDate(LocalDate v){reviewDate=v;} public Double getRating(){return rating;} public void setRating(Double v){rating=v;}
 public String getGoals(){return goals;} public void setGoals(String v){goals=v;} public String getFeedback(){return feedback;} public void setFeedback(String v){feedback=v;}
}
