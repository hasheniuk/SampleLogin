package net.samplelogin.validator;

import net.samplelogin.form.RegistrationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.lang.invoke.MethodHandles;
import java.util.regex.Pattern;

@Component
public class RegistrationFormValidator implements Validator {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");

    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationForm form = (RegistrationForm) o;

        if (!EMAIL_PATTERN.matcher(form.getEmail()).matches()) {
            errors.rejectValue("email", null, "Incorrect email");
        }

        if (!PASSWORD_PATTERN.matcher(form.getPassword()).matches()) {
            String msg = "Password must contains minimum 8 characters at least 1 letter, 1 number and 1 special character";
            errors.rejectValue("password", null, msg);
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", null, "Password not confirmed");
        }

        logger.debug("Found {} errors after validation", errors.getAllErrors().size());
    }
}
