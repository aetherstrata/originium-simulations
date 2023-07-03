package dev.aest.ark.service;

import dev.aest.ark.entity.LocalCredentials;
import dev.aest.ark.entity.User;
import dev.aest.ark.model.RegistrationFormData;
import dev.aest.ark.repository.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CredentialsService implements UserDetailsService
{
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfilePictureService profilePictureService;

    @Transactional
    public LocalCredentials registerNewUser(RegistrationFormData formData, MultipartFile picture) {
        User user = new User();
        user.setNickname(formData.getUsername());
        user.setEmail(formData.getEmail());
        if (!picture.isEmpty()){
            user.setProfilePicture(profilePictureService.save(picture));
        }
        LocalCredentials credentials = new LocalCredentials();
        credentials.setUser(user);
        credentials.setUsername(formData.getUsername());
        credentials.setPassword(this.passwordEncoder.encode(formData.getPassword()));
        credentials.setAuthority(LocalCredentials.DEFAULT_AUTHORITY);
        this.credentialsRepository.save(credentials);
        return credentials;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return credentialsRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
