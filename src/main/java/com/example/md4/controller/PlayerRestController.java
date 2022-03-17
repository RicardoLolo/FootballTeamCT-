package com.example.md4.controller;

import com.example.md4.model.*;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/players")
public class PlayerRestController {
    @Autowired
    private IPlayerService playerService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IAccountService accountService;

    @GetMapping
    public ResponseEntity<Iterable<Player>> getPlayers() {
        Iterable<Player> players = playerService.findAll();
        if (!players.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(players, HttpStatus.OK);
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

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Optional<Player> playerOptional = playerService.findById(id);
        return playerOptional.map
                (player -> new ResponseEntity<>(player, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player player1 = playerService.save(player);
        Player playerNew = playerService.findPlayerLast(player.getId());
        Account account = new Account();
        account.setGmail(playerNew.getGmail());
        account.setPassword(passwordEncoder.encode(playerNew.getPassword()));
        account.setPlayer(playerService.findById(playerNew.getId()).get());
        account.setRoles((Set<Role>) roleService.findByName("PLAYER"));
        accountService.save(account);
        return new ResponseEntity<>(player1, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Player> editPlayer(@RequestBody Player player, @PathVariable Long id) {
        Optional<Player> playerOptional = playerService.findById(id);
        if (!playerOptional.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        player = playerService.save(player);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePlayer(@PathVariable Long id) {
        Optional<Player> playerOptional = playerService.findById(id);
        if (!playerOptional.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        playerService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Player>> getPlayerByName(@RequestParam("search") String search) {
        Iterable<Player> players = playerService.findAllByName(search);
        if (!players.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Player>> showAllPage(@PageableDefault(value = 2) Pageable pageable) {
        Page<Player> playerPage = playerService.findPage(pageable);
        if (!playerPage.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(playerPage, HttpStatus.OK);
    }
}
