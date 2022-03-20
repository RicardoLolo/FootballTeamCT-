package com.example.md4.controller;

import com.example.md4.model.*;
import com.example.md4.repository.IAccountRepository;
import com.example.md4.repository.IPlayerRepository;
import com.example.md4.service.Account.IAccountService;
import com.example.md4.service.player.IPlayerService;
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
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IAccountService accountService;

    @Value("${upload_file_avatar}")
    private String upload_file_avatar;

    @Value("${upload_file_background}")
    private String upload_file_background;

    @Autowired
    private IPlayerService playerService;

    @Autowired
    private IPlayerRepository playerRepository;

    @GetMapping("/list-player")
    public ResponseEntity<Iterable<Player>> getPlayers() {
        Iterable<Player> players = playerService.findAll();
        if (!players.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping("/pagePlayer")
    public ResponseEntity<Page<Player>> showPagePlayer(@PageableDefault(value = 8) Pageable pageable) {
        Page<Player> player_page = playerService.findPage(pageable);
        if (!player_page.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(player_page, HttpStatus.OK);
    }

    @GetMapping("/position")
    public ResponseEntity<Iterable<Position>> getPosition() {
        Iterable<Position> playerPositions = playerService.findAllPosition();
        if (!playerPositions.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(playerPositions, HttpStatus.OK);
    }

    @GetMapping("/performance")
    public ResponseEntity<Iterable<Performance>> getPerformance() {
        Iterable<Performance> playerPerformance = playerService.findAllPerformance();
        if (!playerPerformance.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(playerPerformance, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<Iterable<Status>> getStatus() {
        Iterable<Status> playerStatus = playerService.findAllStatus();
        if (!playerStatus.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(playerStatus, HttpStatus.OK);
    }

    @GetMapping("/find-player-by-id/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Optional<Player> playerOptional = playerService.findById(id);
        return playerOptional.map(player -> new ResponseEntity<>(player, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/find-player-by-gmail")
    public ResponseEntity<Player> getPlayerByGmail(@RequestParam("gmail") String mail) {
        Optional<Player> player = playerService.findByGmail(mail);
        if (!player.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(player.get(), HttpStatus.OK);
    }

    @PostMapping("/save-player")
    public ResponseEntity<Player> createPlayer(@RequestPart("player") Player player,
                                               @RequestPart("avaFile-player") MultipartFile avaFile,
                                               @RequestPart("backGroundFile-player") MultipartFile backGroundFile) {
        if (accountService.existsByGmail(player.getGmail())){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            String avaFileName = avaFile.getOriginalFilename();
            String backGroundFileName = backGroundFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(avaFile.getBytes(), new File(upload_file_avatar + avaFileName));
                player.setAvatarURL("webapp/Images/Avatar" + avaFileName);
            } catch (IOException e) {
                player.setAvatarURL("webapp/Images/Ghostblade (P.2)_ (20).jpg");
                e.printStackTrace();
            }
            try {
                FileCopyUtils.copy(backGroundFile.getBytes(), new File(upload_file_background + backGroundFileName));
                player.setAvatarBackGround("webapp/Images/BackGround" + backGroundFileName);
            } catch (IOException e) {
                player.setAvatarURL("webapp/Images/Ghostblade (P.2)_ (20).jpg");
                e.printStackTrace();
            }
            player.setBMI(player.getWeight() / (player.getHeight() * player.getHeight()));
            Player player1 = playerService.save(player);
            Player playerNew = playerRepository.findPlayerLast();
            Account account = new Account();
            account.setGmail(playerNew.getGmail());
            account.setPassword(passwordEncoder.encode(playerNew.getPassword()));
            account.setPlayer(playerService.findById(playerNew.getId()).get());
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.findById(3L).get());
            account.setRoles(roles);
            accountService.save(account);
            return new ResponseEntity<>(player1, HttpStatus.CREATED);
        }
    }

    @PutMapping("/edit-player/{id}")
    public ResponseEntity<Optional<Player>> editPlayer(@RequestPart("player") Player playerEdit,
                                           @PathVariable("id") Long id,
                                           @RequestPart("avaFile-player") MultipartFile avaFile,
                                           @RequestPart("backGroundFile-player") MultipartFile backGroundFile) {
        Optional<Player> player = playerService.findById(id);
        String avaFileName = avaFile.getOriginalFilename();
        String backGroundFileName = backGroundFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(avaFile.getBytes(), new File(upload_file_avatar + avaFileName));
            playerEdit.setAvatarURL("webapp/Images/Avatar" + avaFileName);
        } catch (IOException e) {
            playerEdit.setAvatarURL(player.get().getAvatarURL());
            e.printStackTrace();
        }
        try {
            FileCopyUtils.copy(backGroundFile.getBytes(), new File(upload_file_background + backGroundFileName));
            playerEdit.setAvatarBackGround("webapp/Images/BackGround" + backGroundFileName);
        } catch (IOException e){
            playerEdit.setAvatarURL(player.get().getAvatarBackGround());
            e.printStackTrace();
        }
        playerEdit.setId(player.get().getId());
        playerService.save(playerEdit);
        Optional<Player> playerEditNew = playerService.findById(id);
        Account account = accountRepository.findAccountByPlayer(id);
        account.setGmail(playerEditNew.get().getGmail());
        account.setPassword(passwordEncoder.encode(playerEditNew.get().getPassword()));
        accountService.save(account);
        return new ResponseEntity<>(playerEditNew, HttpStatus.OK);
    }

    @DeleteMapping("/delete-player/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable("id") Long id) {
        Optional<Player> player = playerService.findById(id);
        if (!player.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountRepository.deleteAccountByPlayer(id);
        playerService.remove(id);
        return new ResponseEntity<>(player.get(), HttpStatus.OK);
    }

    @GetMapping("/search-player")
    public ResponseEntity<Iterable<Player>> getPlayerByName(@RequestParam("search") String search) {
        Iterable<Player> players = playerService.findAllByName(search);
        if (!players.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping("/page-player")
    public ResponseEntity<Page<Player>> showAllPage(@PageableDefault(value = 5) Pageable pageable) {
        Page<Player> playerPage = playerService.findPage(pageable);
        if (!playerPage.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(playerPage, HttpStatus.OK);
    }
}
