package com.example.md4.controller;

import com.example.md4.model.Account;
import com.example.md4.model.Coach;
import com.example.md4.model.CoachType;
import com.example.md4.model.Role;
import com.example.md4.repository.ICoachRepository;
import com.example.md4.service.Account.IAccountService;
import com.example.md4.service.coach.ICoachService;
import com.example.md4.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/coaches")
public class CoachWebController {

    @Autowired
    private ICoachService iCoachService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private ICoachRepository iCoachRepository;

//Lấy list theo database
    @GetMapping
    public ResponseEntity<Iterable<Coach>> showAll() {
        Iterable<Coach> coaches = iCoachService.findAll();
        if (!coaches.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Coach>> showAllPage(@PageableDefault(value = 2) Pageable pageable) {
        Page<Coach> coaches = iCoachService.findPage(pageable);
        if (!coaches.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }

    @GetMapping("/type")
    public ResponseEntity<Iterable<CoachType>> showAllType() {
        Iterable<CoachType> coachTypes = iCoachService.findAllType();
        if (!coachTypes.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coachTypes, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Coach> showOne(@PathVariable("id") Long id) {
        Optional<Coach> coach = iCoachService.findOne(id);
        if (!coach.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(coach.get(), HttpStatus.OK);
    }
//tạo mới 1 đối tượng
    @PostMapping
    public ResponseEntity<Coach> createCoach(@RequestBody Coach coach) {
        Coach coachCreate = iCoachService.save(coach);
        Coach coachNew = iCoachRepository.findCoachLast();
        Account account = new Account();
        account.setGmail(coachNew.getGmail());
        account.setPassword(passwordEncoder.encode(coachNew.getPassword()));
        account.setCoach(iCoachService.findOne(coachNew.getId()).get());
        account.setRoles((Set<Role>) roleService.findByName("COACH"));
        accountService.save(account);
        return new ResponseEntity<>(coachCreate, HttpStatus.CREATED);
    }
//Cập nhật
    @PutMapping("{id}")
    public ResponseEntity<Coach> editCoach(@RequestBody Coach coachEdit, @PathVariable("id") Long id) {
        Optional<Coach> coach = iCoachService.findOne(id);

        if (!coach.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        coachEdit.setId(coach.get().getId());
        coachEdit = iCoachService.save(coachEdit);
        Coach editCoach = iCoachRepository.editCoach();
        Account account = new Account();
        account.setGmail(editCoach.getGmail());
        account.setPassword(passwordEncoder.encode(editCoach.getPassword()));
        account.setCoach(iCoachService.findOne(editCoach.getId()).get());
        accountService.save(account);
        return new ResponseEntity<>(coachEdit, HttpStatus.OK);
    }
    //Xóa 1 đối tượng theo id
    @DeleteMapping("{id}")
    public ResponseEntity<Coach> delete(@PathVariable("id") Long id) {
        Optional<Coach> coach = iCoachService.findOne(id);
        if (!coach.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iCoachService.delete(id);
        return new ResponseEntity<>(coach.get(), HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<Iterable<Coach>> showAllByName(@RequestParam("search") String search) {
        Iterable<Coach> products = iCoachService.findAllByName(search);
        if (!products.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam("name")String name) {
        System.out.println(file.getOriginalFilename());
        System.out.println(name);
        return new ResponseEntity<>("Done",HttpStatus.OK);
    }

    @PostMapping("/upload1")
    public ResponseEntity<String> upload1(@RequestPart("file")MultipartFile file,
                                          @RequestPart("coach") Coach coach) {
        System.out.println(file.getOriginalFilename());
        System.out.println(coach.getName());
        coach.setAvatarURL(file.getOriginalFilename());
        coach.setAvatarBackGround(file.getOriginalFilename());
        CoachType coachType = new CoachType();
        coachType.setId(1L);
        coach.setCoachType(coachType);
        iCoachService.save(coach);
        return new ResponseEntity<>("Done",HttpStatus.OK);
    }

}
