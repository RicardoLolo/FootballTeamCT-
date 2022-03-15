package com.example.md4.controller;

import com.example.md4.model.Account;
import com.example.md4.model.Coach;

import com.example.md4.model.Role;
import com.example.md4.repository.ICoachRepository;
import com.example.md4.service.account.IAccountService;
import com.example.md4.service.coach.ICoachService;
import com.example.md4.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/coach")
@ComponentScan("")
public class CoachController {
    @Autowired
    private ICoachService coachService;

    @Autowired
    private ICoachRepository coachRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IAccountService accountService;


    @Value("${upload_file_ava}")
    private String upload_file_ava;

    @Value("${upload_file_background}")
    private String upload_file_background;


    @GetMapping("/create")
    public ModelAndView createCoach() {
        ModelAndView modelAndView = new ModelAndView("forms.html");
        modelAndView.addObject("coach", new Coach());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute Coach coach) {
        ModelAndView modelAndView = new ModelAndView("forms.html");
        MultipartFile multipartFile = coach.getAvaFile();
        MultipartFile multipartFile1 = coach.getBackGroundFile();
        String fileName = multipartFile.getOriginalFilename();
        String fileName1 = multipartFile1.getOriginalFilename();
        try {
            FileCopyUtils.copy(coach.getAvaFile().getBytes(), new File(upload_file_ava + fileName));
            FileCopyUtils.copy(coach.getAvatarBackGround().getBytes(), new File(upload_file_background + fileName1));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        coachService.save(coach);
        Coach coachNew = coachRepository.findCoachLast();
        Account account = new Account();
        account.setGmail(coachNew.getGmail());
        account.setPassword(passwordEncoder.encode(coachNew.getPassword()));
        account.setCoach(coachService.findById(coachNew.getId()).get());
        account.setRoles((Set<Role>) roleService.findByName("COACH"));
        accountService.save(account);
//        modelAndView.addObject("coaches", coaches);
        return modelAndView;
    }


    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Coach> coach = coachService.findById(id);
        if (coach.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/profile_coach");
            modelAndView.addObject("coach", coach.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/edit")
    public ModelAndView updateCustomer(@ModelAttribute("coach") Coach coach) {
        coachService.save(coach);
        ModelAndView modelAndView = new ModelAndView("/profile_couch");
        modelAndView.addObject("coach", coach);
        modelAndView.addObject("message", "Customer updated successfully");
        return modelAndView;
    }

}
