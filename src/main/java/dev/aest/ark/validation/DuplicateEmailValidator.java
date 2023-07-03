package dev.aest.ark.validation;

import dev.aest.ark.entity.User;
import dev.aest.ark.model.RegistrationFormData;
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
        return RegistrationFormData.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationFormData user = (RegistrationFormData) target;

        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            errors.reject("email.duplicate");
        }
    }
}
