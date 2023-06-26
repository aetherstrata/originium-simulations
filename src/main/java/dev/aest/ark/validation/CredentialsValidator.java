package dev.aest.ark.validation;

import dev.aest.ark.model.LocalCredentials;
import dev.aest.ark.repository.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CredentialsValidator implements Validator
{
    private final CredentialsRepository credentialsRepository;

    @Override
    public boolean supports(Class<?> type) {
        return LocalCredentials.class.equals(type);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LocalCredentials credentials = (LocalCredentials)target;
        if(credentialsRepository.findByUsername(credentials.getUsername()).isPresent()){
            errors.reject("username.duplicate");
        }
    }
}
