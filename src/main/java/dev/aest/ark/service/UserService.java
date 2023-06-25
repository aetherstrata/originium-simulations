package dev.aest.ark.service;

import dev.aest.ark.auth.OAuth2Credentials;
import dev.aest.ark.model.LocalCredentials;
import dev.aest.ark.model.User;
import dev.aest.ark.repository.CredentialsRepository;
import dev.aest.ark.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final CredentialsRepository credentialsRepository;

    /**
     * Retrieve a {@link User} from the database based on its ID.
     * @param id the id of the {@link User} to retrieve from the database
     * @return the retrieved {@link User}, or null if no {@link User} with the passed ID could be found in the database
     */
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    /**
     * Retrieve the current {@link User} from the database.
     * @return the retrieved {@link User}, or null if no {@link User} is logged in
     */
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Principal principal = (Principal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal == null
            ? null
            : principal instanceof OAuth2AuthenticationToken token
                ? ((OAuth2Credentials)token.getPrincipal()).getUser()
                : this.credentialsRepository.findByUsername(principal.getName()).map(LocalCredentials::getUser).orElse(null);
    }

    /**
     * Save a {@link User} in the database.
     * @param user the {@link User} to save into the database
     * @throws DataIntegrityViolationException if a {@link User} with the same username
     *                              as the passed User already exists in the database
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    /**
     * Retrieve all {@link User Users} from the database.
     * @return a List with all the retrieved {@link User Users}
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        Iterable<User> iterable = this.userRepository.findAll();
        for(User user : iterable)
            result.add(user);
        return result;
    }
}