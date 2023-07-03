package dev.aest.ark.validation;

import dev.aest.ark.entity.LocalCredentials;
import dev.aest.ark.model.RegistrationFormData;
import dev.aest.ark.repository.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class DuplicateUsernameValidator implements Validator
{
    private final CredentialsRepository credentialsRepository;

    @Override
    public boolean supports(Class<?> type) {
        return RegistrationFormData.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationFormData credentials = (RegistrationFormData) target;
        if(credentialsRepository.findByUsername(credentials.getUsername()).isPresent()){
            errors.reject("username.duplicate");
        }
    }
}
