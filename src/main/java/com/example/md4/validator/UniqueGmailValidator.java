package com.example.md4.validator;

import com.example.md4.model.Player;
import com.example.md4.service.account.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class UniqueGmailValidator implements ConstraintValidator<UniqueGmail, Player> {
    @Autowired
    private PlayerService playerSevice;

    @Override
    public boolean isValid(Player value, ConstraintValidatorContext context) {
        return playerSevice
                .findAll()
                .stream()
                .filter(c -> isSameGmail(value, c))
                .allMatch(c -> isSameId(value, c));
    }

    private boolean isSameGmail(Player c1, Player c2) {
        return Objects.equals(c1.getGmail(), c2.getGmail());
    }

    private boolean isSameId(Player c1, Player c2) {
        return Objects.equals(c1.getId(), c2.getId());
    }
}

