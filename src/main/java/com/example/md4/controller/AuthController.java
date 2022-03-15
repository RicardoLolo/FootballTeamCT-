package com.example.md4.controller;

import com.example.md4.model.Account;
import com.example.md4.model.JwtResponse;
import com.example.md4.model.Role;
import com.example.md4.service.account.IAccountService;
import com.example.md4.service.jwtService.JwtService;
import com.example.md4.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(account.getGmail(), account.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account currentUser = accountService.findByGmail(account.getGmail()).get();
        return ResponseEntity.ok(new JwtResponse(currentUser.getId(),jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<Optional<Role>> roleById(@RequestParam("id") Long id) {
        Optional<Role> roles = roleService.findById(id);
        return ResponseEntity.ok(roles);
    }
    @PostConstruct
    public void init() {
        List<Account> accounts = (List<Account>) accountService.findAll();
        if (accounts.isEmpty()) {
            Account account = new Account();
            account.setGmail("loloringo9999@gmail.com");
            account.setPassword(passwordEncoder.encode("Lolomomo"));
            accountService.save(account);
        }
    }
}


