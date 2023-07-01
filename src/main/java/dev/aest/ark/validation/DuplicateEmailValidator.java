package dev.aest.ark.validation;

import dev.aest.ark.model.User;
import dev.aest.ark.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class DuplicateEmailValidator implements Validator
{
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> type) {
        return User.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;

        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            errors.reject("email.duplicate");
        }
    }
}
