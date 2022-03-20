package com.example.md4.controller;

import com.example.md4.model.Account;
import com.example.md4.model.Coach;
import com.example.md4.model.CoachType;
import com.example.md4.model.Role;
import com.example.md4.repository.IAccountRepository;
import com.example.md4.repository.ICoachRepository;
import com.example.md4.service.Account.IAccountService;
import com.example.md4.service.coach.ICoachService;
import com.example.md4.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/coach")
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

    @Autowired
    private IAccountRepository accountRepository;

    @Value("${upload_file_avatar}")
    private String upload_file_avatar;

    @Value("${upload_file_background}")
    private String upload_file_background;

//Lấy list theo database
    @GetMapping("/list-coach")
    public ResponseEntity<Iterable<Coach>> showAll() {
        Iterable<Coach> coaches = iCoachService.findAll();
        if (!coaches.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }

    @GetMapping("/page-coach")
    public ResponseEntity<Page<Coach>> showAllPage(@PageableDefault(value = 5) Pageable pageable) {
        Page<Coach> coaches = iCoachService.findPage(pageable);
        if (!coaches.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }

    @GetMapping("/type-coach")
    public ResponseEntity<Iterable<CoachType>> showAllType() {
        Iterable<CoachType> coachTypes = iCoachService.findAllType();
        if (!coachTypes.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(coachTypes, HttpStatus.OK);
    }
    @GetMapping("/find-coach-by-id/{id}")
    public ResponseEntity<Coach> findById(@PathVariable("id") Long id) {
        Optional<Coach> coach = iCoachService.findOne(id);
        if (!coach.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(coach.get(), HttpStatus.OK);
    }

    @GetMapping("/find-coach-by-gmail")
    public ResponseEntity<Coach> findByGmail(@RequestParam("gmail") String mail) {
        Optional<Coach> coach = iCoachService.findByGmail(mail);
        if (!coach.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(coach.get(), HttpStatus.OK);
    }

    //tạo mới 1 đối tượng
    @PostMapping("/save-coach")
    public ResponseEntity<Coach> createCoach(@RequestPart("coach") Coach coach,
                                             @RequestPart("avaFile-coach") MultipartFile avaFile,
                                             @RequestPart("backGroundFile-coach") MultipartFile backGroundFile) {
        if (accountService.existsByGmail(coach.getGmail())){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            String avaFileName = avaFile.getOriginalFilename();
            String backGroundFileName = backGroundFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(avaFile.getBytes(), new File(upload_file_avatar + avaFileName));
                coach.setAvatarURL("webapp/Images/Avatar" + avaFileName);
            } catch (IOException e) {
                coach.setAvatarURL("webapp/Images/Ghostblade (P.2)_ (20).jpg");
                e.printStackTrace();
            }
            try {
                FileCopyUtils.copy(backGroundFile.getBytes(), new File(upload_file_background + backGroundFileName));
                coach.setAvatarBackGround("webapp/Images/BackGround" + backGroundFileName);
            } catch (IOException e){
                coach.setAvatarURL("webapp/Images/Ghostblade (P.2)_ (20).jpg");
                e.printStackTrace();
            }
            Coach coachCreate = iCoachService.save(coach);
            Coach coachNew = iCoachRepository.findCoachLast();
            Account account = new Account();
            account.setGmail(coachNew.getGmail());
            account.setPassword(passwordEncoder.encode(coachNew.getPassword()));
            account.setCoach(iCoachService.findOne(coachNew.getId()).get());
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.findById(2L).get());
            account.setRoles(roles);
            accountService.save(account);
            return new ResponseEntity<>(coachCreate, HttpStatus.CREATED);
        }
    }

    //Cập nhật
    @PutMapping("/edit-coach/{id}")
    public ResponseEntity<Optional<Coach>> editCoach(@RequestPart("coach") Coach coachEdit,
                                           @PathVariable("id") Long id,
                                           @RequestPart("avaFile_coach") MultipartFile avaFile,
                                           @RequestPart("backGroundFile_coach") MultipartFile backGroundFile) {
        Optional<Coach> coach = iCoachService.findOne(id);
        String avaFileName = avaFile.getOriginalFilename();
        String backGroundFileName = backGroundFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(avaFile.getBytes(), new File(upload_file_avatar + avaFileName));
            coachEdit.setAvatarURL("webapp/Images/Avatar" + avaFileName);
        } catch (IOException e) {
            coachEdit.setAvatarURL(coach.get().getAvatarURL());
            e.printStackTrace();
        }
        try {
            FileCopyUtils.copy(backGroundFile.getBytes(), new File(upload_file_background + backGroundFileName));
            coachEdit.setAvatarBackGround("webapp/Images/BackGround" + backGroundFileName);
        } catch (IOException e){
            coachEdit.setAvatarURL(coach.get().getAvatarBackGround());
            e.printStackTrace();
        }
        coachEdit.setId(coach.get().getId());
        iCoachService.save(coachEdit);
        Optional<Coach> editCoach = iCoachService.findOne(id);
        Account account = accountRepository.findAccountByCoach(id);
        account.setGmail(editCoach.get().getGmail());
        account.setPassword(passwordEncoder.encode(editCoach.get().getPassword()));
        accountService.save(account);
        return new ResponseEntity<>(editCoach, HttpStatus.OK);
    }

    //Xóa 1 đối tượng theo id
    @DeleteMapping("/delete-coach/{id}")
    public ResponseEntity<Coach> delete(@PathVariable("id") Long id) {
        Optional<Coach> coach = iCoachService.findOne(id);
        if (!coach.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountRepository.deleteAccountByCoach(id);
        iCoachService.delete(id);
        return new ResponseEntity<>(coach.get(), HttpStatus.OK);
    }

    @GetMapping("/search-coach")
    public ResponseEntity<Iterable<Coach>> showAllByName(@RequestParam("search") String search) {
        Iterable<Coach> products = iCoachService.findAllByName(search);
        if (!products.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/totalCoachSalary")
    public ResponseEntity<?> totalCoachSalary(){
        Double totalCoachSalary = iCoachRepository.totalCoachSalary();
        return new ResponseEntity<>(totalCoachSalary, HttpStatus.OK);
    }

    @GetMapping("/totalCoachBonus")
    public ResponseEntity<?> totalCoachBonus(){
        Double totalCoachBonus = iCoachRepository.totalCoachBonus();
        return new ResponseEntity<>(totalCoachBonus, HttpStatus.OK);
    }
}