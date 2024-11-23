package com.example.dziekanat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidateLoginFieldsTest {

    @Test
    public void testValidateLoginFields_emptyLogin() {
        boolean result = Helper.validateLogin("", "password123");
        assertFalse(result);
    }

    @Test
    public void testValidateLoginFields_emptyPassword() {
        boolean result = Helper.validateLogin("username", "");
        assertFalse(result);
    }

    @Test
    public void testValidateLoginFields_validFields() {
        boolean result = Helper.validateLogin("username", "password123");
        assertTrue(result);
    }

    @Test
    public void testValidateGrade_emptyFields() {
        assertFalse(Helper.validateGrade("token123", "", "4.5", "Matematyka"));
        assertFalse(Helper.validateGrade("token123", "12345", "", "Matematyka"));
        assertFalse(Helper.validateGrade("token123", "12345", "4.5", ""));
    }

    @Test
    public void testValidateGrade_invalidIndex() {
        assertFalse(Helper.validateGrade("token123", "123 45", "4.5", "Matematyka")); // spacje w index
        assertFalse(Helper.validateGrade("token123", "123a45", "4.5", "Matematyka")); // litery w index
        assertFalse(Helper.validateGrade("token123", "123@45", "4.5", "Matematyka")); // znaki specjalne w index
    }

    @Test
    public void testValidateGrade_invalidGrade() {
        assertFalse(Helper.validateGrade("token123", "12345", "abc", "Matematyka"));
    }

    @Test
    public void testValidateGrade_invalidSubject() {
        assertFalse(Helper.validateGrade("token123", "12345", "4.5", "Matematyka123"));
    }

    @Test
    public void testValidateGrade_validFields() {
        assertTrue(Helper.validateGrade("token123", "12345", "4.5", "Matematyka"));
    }
}
