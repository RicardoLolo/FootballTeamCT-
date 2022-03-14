package com.example.md4.controller;

import com.example.md4.model.Coach;

import com.example.md4.service.ICoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/coach")
public class CoachController {
    @Autowired
    private ICoachService coachService;



    @Value("${upload_file_ava}")
    private String upload_file_ava;

    @Value("${upload_file_background}")
    private String upload_file_background;



    @GetMapping("/create")
    public ModelAndView createCoach() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("coach", new Coach());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute Coach coach) {
        ModelAndView modelAndView = new ModelAndView("create");
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

        String message = coachService.save(coach);
        Iterable<Coach> coaches = coachService.findAll();
        modelAndView.addObject("coaches", coaches);
        modelAndView.addObject("message", message);
        return modelAndView;
    }


    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        Optional<Coach> coach = coachService.findOne(id);
        coach.ifPresent(value -> modelAndView.addObject("coach", value));
        return modelAndView;
    }
    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute Optional<Coach> coach, @PathVariable Long id) {
        if (coach.isPresent()) {
            Coach coachEdit = coach.get();
            coachEdit.setId(id);
            coachService.save(coachEdit);
        }
        return "redirect:/coach";
    }

}
