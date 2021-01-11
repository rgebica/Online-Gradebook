package com.index.authTests;

import com.index.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest {

    @Test
    void isNotEnabled() {
        User newUser = new User();
        assertFalse(newUser.isEnabled());
    }

    @Test
    void isEnabled() {
        User newUser = new User();
        newUser.setEnabled(true);
        assertTrue(newUser.isEnabled());
    }
}
