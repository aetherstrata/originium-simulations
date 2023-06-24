package dev.aest.ark.service;

import dev.aest.ark.model.LocalCredentials;
import dev.aest.ark.model.User;
import dev.aest.ark.repository.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CredentialsService implements UserDetailsService
{
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean isOAuth(){
        return getCurrentAuth() instanceof OAuth2AuthenticationToken;
    }

    public boolean isAdminUser(){
        Authentication auth = getCurrentAuth();
        return auth != null && auth.getAuthorities().stream().anyMatch(x -> x.getAuthority().equals(LocalCredentials.ADMIN_AUTHORITY));
    }

    @Transactional(readOnly = true)
    public LocalCredentials getCredentials(User user) {
        return getCredentials(user.getId());
    }

    @Transactional(readOnly = true)
    public LocalCredentials getCredentials(Long id) {
        Optional<LocalCredentials> result = this.credentialsRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional(readOnly = true)
    public LocalCredentials getCredentials(String username) {
        Optional<LocalCredentials> result = this.credentialsRepository.findByUsername(username);
        return result.orElse(null);
    }

    @Transactional
    public LocalCredentials saveCredentials(LocalCredentials credentials) {
        credentials.setAuthority(LocalCredentials.DEFAULT_AUTHORITY);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return credentialsRepository.findByUsername(username).orElse(null);
    }

    private Authentication getCurrentAuth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
