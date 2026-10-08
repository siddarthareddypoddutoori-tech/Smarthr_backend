package com.smarthr.controller;
import com.smarthr.model.*; import com.smarthr.repo.EmployeeRepository; import com.smarthr.security.JwtService; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.http.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 public record Login(@Email String email,@NotBlank String password,String role) {}
 public record Register(@NotBlank String name,@Email String email,@Size(min=6) String password) {}
 private final EmployeeRepository repo; private final PasswordEncoder encoder; private final JwtService jwt;
 public AuthController(EmployeeRepository r,PasswordEncoder e,JwtService j){repo=r;encoder=e;jwt=j;}
 @PostMapping("/login") public ResponseEntity<?> login(@RequestBody @Valid Login x){
  return repo.findByEmail(x.email())
   .filter(e->encoder.matches(x.password(),e.getPassword()))
   .<ResponseEntity<?>>map(e->{
    boolean roleMatches=x.role()==null||x.role().isBlank()
     ||("HR".equalsIgnoreCase(x.role())&&(e.getRole()==Role.ADMIN||e.getRole()==Role.HR))
     ||x.role().equalsIgnoreCase(e.getRole().name());
    if(!roleMatches)return ResponseEntity.status(HttpStatus.FORBIDDEN).body(java.util.Map.of("message","This account does not have the selected role"));
    return ResponseEntity.ok(java.util.Map.of("token",jwt.generate(e.getEmail(),e.getRole().name()),"tokenType","Bearer","user",safe(e)));
   }).orElseGet(()->ResponseEntity.status(401).body(java.util.Map.of("message","Invalid email or password")));
 }
 @PostMapping("/register") public ResponseEntity<?> register(@RequestBody @Valid Register x){if(repo.findByEmail(x.email()).isPresent())return ResponseEntity.badRequest().body(java.util.Map.of("message","Email already exists"));Employee e=new Employee();e.setName(x.name());e.setEmail(x.email());e.setPassword(encoder.encode(x.password()));e.setRole(Role.EMPLOYEE);repo.save(e);return ResponseEntity.ok(java.util.Map.of("message","Registration successful"));}
 static java.util.Map<String,Object> safe(Employee e){return java.util.Map.of("id",e.getId(),"name",e.getName(),"email",e.getEmail(),"role",e.getRole(),"department",e.getDepartment()==null?"":e.getDepartment());}
}
