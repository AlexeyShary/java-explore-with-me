package ru.practicum.ewm.service.user.data.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserEmailValidator implements ConstraintValidator<UserEmail, String> {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Override
    public void initialize(UserEmail userEmail) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true;
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return false;
        }

        String[] parts = email.split("@");

        String login = parts[0];
        if (login.length() > 64) {
            return false;
        }

        String domain = parts[1];
        String[] domainParts = domain.split("\\.");
        for (String domainPart : domainParts) {
            if (domainPart.length() > 63) {
                return false;
            }
        }

        return true;
    }
}
